package se.beatit.hshserver.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

/**
 * Created by stefan on 3/13/16.
 */
@Entity
@Table(name="home")
public class Home implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy="home", fetch = FetchType.EAGER)
    private Set<ElectricityConsumption> electricityConsumption = Collections.EMPTY_SET;

    @OneToMany(mappedBy="home", fetch = FetchType.LAZY)
    private Set<Sensor> sensor = Collections.EMPTY_SET;

    public Home() {}

    public Home(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ElectricityConsumption> getElectricityConsumption() {
        return electricityConsumption;
    }

    public void setElectricityConsumption(Set<ElectricityConsumption> electricityConsumption) {
        this.electricityConsumption = electricityConsumption;
    }


    public Set<Sensor> getSensor() {
        return sensor;
    }

    public void setSensor(Set<Sensor> sensor) {
        this.sensor = sensor;
    }

    public  boolean hasSensor(String name) {
        return sensor.stream().anyMatch(s -> s.getName().equals(name));
    }

    public  Sensor getSensor(String name) {
        return sensor.stream().filter(s -> s.getName().equals(name)).findFirst().get();
    }

    public void addSensor(Sensor s) {
        sensor.add(s);
    }

}
