package se.beatit.hshserver.rest.api;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.beatit.hshserver.entities.Home;
import se.beatit.hshserver.rest.resource.HomeRS;
import se.beatit.hshserver.rest.resource.TemperatureSensorRS;
import se.beatit.hshserver.service.ElectricityService;
import se.beatit.hshserver.service.HomeService;
import se.beatit.hshserver.service.TemperatureService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by stefan on 3/13/16.
 */
@RestController
@RequestMapping("/homes")
public class Homes {

    private SimpleDateFormat dateFormat = new SimpleDateFormat(HomeRS.DATE_FORMAT);

    @Autowired
    private HomeService homeService;

    @Autowired
    private ElectricityService electricityService;

    @Autowired
    private TemperatureService temperatureService;


    @RequestMapping(method = RequestMethod.POST, value = {"{homename}"})
    public HomeRS createHome(@PathVariable String homename) {
        return new HomeRS(homeService.save(new Home(homename)));
    }

    @RequestMapping(method = RequestMethod.GET, value = {"{homename}"})
    public HomeRS getHome(@PathVariable String homename) {
        Home home = homeService.findByName(homename);

        HomeRS homeRS = new HomeRS(home);
        homeRS.setName(home.getName());
        homeRS.setCurrentElectricityUsage(electricityService.getCurrentUsage(home));
        homeRS.setCurrentTemperatures(temperatureService.getCurrentTemperatures(home));

        return homeRS;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"{homename}/history/{fromdate}/{todate}"})
    public HomeRS getHome(@PathVariable String homename, @PathVariable String fromdate, @PathVariable String todate) {
        Home home = homeService.findByName(homename);

        HomeRS homeRS = new HomeRS(home);
        homeRS.setName(home.getName());
        homeRS.setCurrentElectricityUsage(electricityService.getCurrentUsage(home));
        homeRS.setCurrentTemperatures(temperatureService.getCurrentTemperatures(home));

        try {
            Date from = dateFormat.parse(fromdate);
            Date to = dateFormat.parse(todate);

            homeRS.setElectricityConsumptionHistory(electricityService.getHistoricUsage(home, from, to));

            Map<String, Map<Date, Float>> tempHistory = temperatureService.getHistoricTemperatures(home, from, to);
            homeRS.setTemperatureHistory(createSensorHistory(tempHistory));
        } catch (ParseException e) {
            //TODO: handle errors
        }

        return homeRS;
    }

    private List<TemperatureSensorRS> createSensorHistory(Map<String, Map<Date, Float>> tempHistory) {
        return tempHistory.keySet().stream().map(s -> new TemperatureSensorRS(s, tempHistory.get(s))).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST, value = {"{homename}/electricity/{wh}"})
    public String addElectricityConsumption(@PathVariable String homename, @PathVariable Long wh) {
        Home home = homeService.findByName(homename);
        electricityService.addElectricityConsumption(home,wh, DateUtils.addMinutes(new Date(), -1), new Date());
        return "OK, electricity consumption added";
    }

    @RequestMapping(method = RequestMethod.POST, value = {"{homename}/temperature/{sensor}/{temperature}"})
    public String addTemperature(@PathVariable String homename, @PathVariable String sensor, @PathVariable Float temperature) {
        Home home = homeService.findByName(homename);
        temperatureService.addTemperature(home, sensor, temperature);
        return "OK, temperature added";
    }

    @RequestMapping(method = RequestMethod.GET, value = "test")
    public HomeRS test() {
        String home = "furubo";
        String sensor1 = "inside";
        String sensor2 = "outside";

        if(homeService.findByName(home) == null) {
            createHome(home);
        }

        addTemperature(home, sensor1, 21.4f+count);
        addTemperature(home, sensor2, -1.4f-count);
        addElectricityConsumption(home, 32l+(int)count);

        count++;

        return getHome(home, "2016-04-17", "2016-04-20");
    }

    private static float count = 0f;
}
