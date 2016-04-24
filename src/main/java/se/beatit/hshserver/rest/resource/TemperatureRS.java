package se.beatit.hshserver.rest.resource;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by stefan on 4/21/16.
 */
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemperatureRS  implements Serializable {
    private Date timestamp;

    private Float temperature;

    private String sensor;

    public TemperatureRS(){}

    public TemperatureRS(String sensor, Float temperature) {
        this.sensor = sensor;
        this.temperature = temperature;
    }

    public TemperatureRS(Date timestamp, Float temperature) {
        this.timestamp = timestamp;
        this.temperature = temperature;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }
}
