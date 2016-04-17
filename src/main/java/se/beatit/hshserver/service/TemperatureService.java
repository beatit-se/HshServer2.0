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
        return home.getSensor().stream().collect(Collectors.toMap(Sensor::getName, s -> getCurrentTemperature(s)));
    }

    public Float getCurrentTemperature(Sensor sensor) {
        return repo.findLatestForSensor(sensor).getTemperature();
    }

    public Map<String,Map<Date,Float>> getHistoricTemperatures(Home home, Date from, Date to) {
        return home.getSensor().stream().collect(Collectors.toMap(Sensor::getName, s -> getHistoricTemperatures(s, from, to)));
    }

    public Map<Date, Float> getHistoricTemperatures(Sensor sensor, Date from, Date to) {
        List<Temperature> temperatures = repo.findForSensor(sensor, from, to);
        return temperatures.stream().collect(Collectors.toMap(Temperature::getTimestamp, Temperature::getTemperature));
    }
}
