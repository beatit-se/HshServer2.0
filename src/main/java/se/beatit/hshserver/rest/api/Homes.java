package se.beatit.hshserver.rest.api;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.beatit.hshserver.entities.Home;
import se.beatit.hshserver.rest.resource.*;
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
        homeRS.setCurrentTemperatures(new TemperaturesRS(
                temperatureService.getCurrentTemperatures(home).entrySet().stream().
                        map(s -> new TemperatureRS(s.getKey(), s.getValue())).collect(Collectors.toList())));

        return homeRS;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"{homename}/history/{fromdate}/{todate}"})
    public HomeRS getHome(@PathVariable String homename, @PathVariable String fromdate, @PathVariable String todate) {
        Home home = homeService.findByName(homename);

        HomeRS homeRS = new HomeRS(home);
        homeRS.setName(home.getName());
        homeRS.setCurrentElectricityUsage(electricityService.getCurrentUsage(home));
        homeRS.setCurrentTemperatures(new TemperaturesRS(
                temperatureService.getCurrentTemperatures(home).entrySet().stream().
                        map(s -> new TemperatureRS(s.getKey(), s.getValue())).collect(Collectors.toList())));


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

    private List<TemperaturesRS> createSensorHistory(Map<String, Map<Date, Float>> tempHistory) {
        return tempHistory.keySet().stream().map(s -> new TemperaturesRS(s, getTemperatureRSList(tempHistory.get(s)))).collect(Collectors.toList());
    }

    private List<TemperatureRS> getTemperatureRSList(Map<Date, Float> dateFloatMap) {
        return dateFloatMap.entrySet().stream().map(s -> new TemperatureRS(s.getKey(), s.getValue())).collect(Collectors.toList());
    }


    @RequestMapping(method = RequestMethod.POST, value = {"{homename}/electricity/{wh}"})
    public ResultRS addElectricityConsumption(@PathVariable String homename, @PathVariable Long wh) {
        Home home = homeService.findByName(homename);
        electricityService.addElectricityConsumption(home, wh, DateUtils.addMinutes(new Date(), -1), new Date());
        return new ResultRS("OK, electricity consumption added", ResultRS.RESULT_OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = {"{homename}/temperature/{sensor}/{temperature}"})
    public ResultRS addTemperature(@PathVariable String homename, @PathVariable String sensor, @PathVariable Float temperature) {
        Home home = homeService.findByName(homename);
        temperatureService.addTemperature(home, sensor, temperature);
        return new ResultRS("OK, temperature added", ResultRS.RESULT_OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "test")
    public HomeRS test() {
        String home = "furubo";
        String sensor1 = "inside";
        String sensor2 = "outside";

        if (homeService.findByName(home) == null) {
            createHome(home);
        }

        addTemperature(home, sensor1, 21.4f + count);
        addTemperature(home, sensor2, -1.4f - count);
        addElectricityConsumption(home, 32l + (int) count);

        count++;

        return getHome(home, "2016-04-20", "2016-04-30");
    }

    private static float count = 0f;
}
