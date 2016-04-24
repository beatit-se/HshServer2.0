package se.beatit.hshserver.rest.resource;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by stefan on 4/21/16.
 */
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemperaturesRS  implements Serializable {
    private String sensor;
    private List<TemperatureRS> temperature;

    public TemperaturesRS() {}

    public TemperaturesRS(List<TemperatureRS> temperature) {
        this.temperature = temperature;
    }

    public TemperaturesRS(String sensor, List<TemperatureRS> temperature) {
        this.sensor = sensor;
        this.temperature = temperature;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public List<TemperatureRS> getTemperature() {
        return temperature;
    }

    public void setTemperature(List<TemperatureRS> temperature) {
        this.temperature = temperature;
    }
}
