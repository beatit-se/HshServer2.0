package se.beatit.hshserver.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.beatit.hshserver.entities.Home;
import se.beatit.hshserver.rest.domain.HomeDTO;
import se.beatit.hshserver.service.ElectricityService;
import se.beatit.hshserver.service.HomeService;
import se.beatit.hshserver.service.TemperatureService;

import java.util.HashMap;

/**
 * Created by stefan on 3/13/16.
 */
@RestController
@RequestMapping("/homes")
public class HomeRS {

    @Autowired
    private HomeService homeService;

    @Autowired
    private ElectricityService electricityService;

    @Autowired
    private TemperatureService temperatureService;


    @RequestMapping(method = RequestMethod.GET, value = {"{name}"})
    public HomeDTO getHome(@PathVariable String name) {
        Home home = homeService.findByName(name);

        HomeDTO homeDTO = new HomeDTO(home);
        homeDTO.setName(home.getName());
        homeDTO.setElectricityUsage(electricityService.getCurrentUsage(home));
        homeDTO.setTemperatures(temperatureService.getCurrentTemperatures(home));

        return homeDTO;
    }

    @RequestMapping(method = RequestMethod.POST, value = {"{name}"})
    public HomeDTO createHome(@PathVariable String name) {
        return new HomeDTO(homeService.save(new Home(name)));
    }
}
