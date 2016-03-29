package se.beatit.hshserver.service;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.beatit.hshserver.entities.Home;
import se.beatit.hshserver.entities.Temperature;
import se.beatit.hshserver.entities.Sensor;
import se.beatit.hshserver.repositories.SensorRepository;
import se.beatit.hshserver.repositories.TemperatureRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by stefan on 3/13/16.
 */
@Component("temperatureService")
@Transactional
public class TemperatureService {

    private final TemperatureRepository repo;

    private final SensorRepository sensorRepo;


    @Autowired
    public TemperatureService(TemperatureRepository repo, SensorRepository sensorRepo) {
        this.repo = repo;
        this.sensorRepo = sensorRepo;
    }

    public Temperature save(Temperature temperature) {
        return repo.save(temperature);
    }

    public Temperature addTemperature(Home home, String sensorName, Float temp) {
        Temperature temperature = new Temperature(temp);

        Sensor sensor = null;
        if(home.hasSensor(sensorName)){
            sensor = home.getSensor(sensorName);
        } else {
            sensor = new Sensor(home, sensorName);
            sensorRepo.save(sensor);
        }

        temperature.setTimestamp(new Date());
        temperature.setSensor(sensor);
        return save(temperature);
    }

    public Map<String,Float> getCurrentTemperatures(Home home) {
        //Map<String,Float> result = new HashMap<String,Float>();
        //home.getSensor().forEach(s -> result.put(s.getName(), getCurrentTemperature(s).getTemperature()));

        return home.getSensor().stream().collect(Collectors.toMap(Sensor::getName, s -> getCurrentTemperature(s)));

        //return result;
    }

    public Float getCurrentTemperature(Sensor sensor) {
        return repo.findLatestForSensor(sensor).getTemperature();
    }

    /*
    public List<Temperature> getHistoricalTemperatures(Home home, String sensor, long from, long to, long timeResolution) {
        List<Temperature> temperatures = new ArrayList<Temperature>();

        Map<Date, Temperature> result = new HashMap<Date, Temperature>();
        Iterable<Temperature> all = repo.findAll();

        all.forEach(temperature -> mapToCorrect(temperature, result));

        return result.values();
    }

    private void mapToCorrect(Temperature temperature, Map<Date, Temperature> result) {
        Date date = DateUtils.round(temperature.getTimestamp(), Calendar.DAY_OF_YEAR);

        Temperature sumTemp = (result.get(date) == null) ? new Temperature() : result.get(date);
        sumTemp.setTimestamp(date);
        sumTemp.setTemperature();
        temperature.getTimestamp()
    }
    */

}
