package se.beatit.hshserver.rest.api;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.beatit.hshserver.entities.Home;
import se.beatit.hshserver.rest.resource.HomeRS;
import se.beatit.hshserver.service.ElectricityService;
import se.beatit.hshserver.service.HomeService;
import se.beatit.hshserver.service.TemperatureService;

import java.util.Date;

/**
 * Created by stefan on 3/13/16.
 */
@RestController
@RequestMapping("/homes")
public class Homes {

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

        HomeRS homeDTO = new HomeRS(home);
        homeDTO.setName(home.getName());
        homeDTO.setCurrentElectricityUsage(electricityService.getCurrentUsage(home));
        homeDTO.setCurrentTemperatures(temperatureService.getCurrentTemperatures(home));

        return homeDTO;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"{homename}/history/{fromdate}/{todate}"})
    public HomeRS getHome(@PathVariable String homename, @PathVariable String fromdate, @PathVariable String todate) {
        Home home = homeService.findByName(homename);

        HomeRS homeDTO = new HomeRS(home);
        homeDTO.setName(home.getName());
        homeDTO.setCurrentElectricityUsage(electricityService.getCurrentUsage(home));
        homeDTO.setCurrentTemperatures(temperatureService.getCurrentTemperatures(home));

        //TODO: Add history for temp and wh

        return homeDTO;
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
}
