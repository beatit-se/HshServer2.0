package se.beatit.hshserver.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.beatit.hshserver.entities.Home;
import se.beatit.hshserver.entities.Temperature;
import se.beatit.hshserver.service.HomeService;
import se.beatit.hshserver.service.TemperatureService;

import java.util.Date;

/**
 * Created by stefan on 3/18/16.
 */
@RestController
@RequestMapping("/homes")
public class TemperatureRS {

    @Autowired
    private HomeService homeService;

    @Autowired
    private TemperatureService temperatureService;



    @RequestMapping(method = RequestMethod.POST, value = {"{homename}/temperature/{sensor}/{temperature}"})
    public Temperature addTemperature(@PathVariable String homename, @PathVariable String sensor, @PathVariable Float temperature) {
        Home home = homeService.findByName(homename);
        return temperatureService.addTemperature(home, sensor, temperature);
    }


    @RequestMapping(method = RequestMethod.GET, value = {"{homename}/temperature/{sensor}"})
    public Temperature getTemperature(@PathVariable String homename, @PathVariable String sensor) {
        Home home = homeService.findByName(homename);
        return temperatureService.getCurrentTemperature(home.getSensor(sensor));
    }
}
