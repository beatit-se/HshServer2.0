package se.beatit.hshserver.rest.domain;

import se.beatit.hshserver.entities.Home;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by stefan on 3/25/16.
 */
@XmlRootElement
public class HomeDTO  implements Serializable {
    public String name;
    public Map<String, Float> temperatures;
    public Long electricityUsage;
    public Date timestamp = new Date();

    public HomeDTO() {
    }

    public HomeDTO(Home home) {
        name = home.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Float> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(Map<String, Float> temperatures) {
        this.temperatures = temperatures;
    }

    public Long getElectricityUsage() {
        return electricityUsage;
    }

    public void setElectricityUsage(Long electricityUsage) {
        this.electricityUsage = electricityUsage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
