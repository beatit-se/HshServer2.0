package se.beatit.hshserver.rest.api;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.beatit.hshserver.entities.ElectricityConsumption;
import se.beatit.hshserver.entities.Home;
import se.beatit.hshserver.service.ElectricityService;
import se.beatit.hshserver.service.HomeService;

import java.util.Date;


/**
 * Created by stefan on 3/18/16.
 */
@RestController
@RequestMapping("/homes")
public class ElectricityRS {

    @Autowired
    private HomeService homeService;

    @Autowired
    private ElectricityService electricityService;

    @RequestMapping(method = RequestMethod.POST, value = {"{homename}/electricity/{wh}"})
    public ElectricityConsumption addElectricityConsumption(@PathVariable String homename, @PathVariable Long wh) {
        Home home = homeService.findByName(homename);
        return electricityService.addElectricityConsumption(home,wh, DateUtils.addMinutes(new Date(), -1), new Date());
    }


    @RequestMapping(method = RequestMethod.GET, value = {"{homename}/electricity"})
    public Long getCurrentUsage(@PathVariable String homename) {
        Home home = homeService.findByName(homename);
        return electricityService.getCurrentUsage(home);
    }

}
