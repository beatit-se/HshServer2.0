package se.beatit.hshserver.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by stefan on 3/25/16.
 */
@Entity
@Table(name="sensor")
public class Sensor   implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="home_id")
    private Home home;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy="sensor", fetch = FetchType.EAGER)
    private Set<Temperature> temperature;

    public Sensor() {
    }

    public Sensor(Home home, String sensorName) {
        setHome(home);
        setName(sensorName);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @JsonIgnore
    public Set<Temperature> getTemperature() {
        return temperature;
    }

    public void setTemperature(Set<Temperature> temperature) {
        this.temperature = temperature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sensor sensor = (Sensor) o;

        if (home != null ? !home.equals(sensor.home) : sensor.home != null) return false;
        return name != null ? name.equals(sensor.name) : sensor.name == null;

    }

    @Override
    public int hashCode() {
        int result = home != null ? home.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
