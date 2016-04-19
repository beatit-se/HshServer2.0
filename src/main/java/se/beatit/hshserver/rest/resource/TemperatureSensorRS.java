package se.beatit.hshserver.rest.resource;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by stefan on 4/19/16.
 */
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemperatureSensorRS implements Serializable {
    private String name;
    private Map<Date, Float> history;

    public TemperatureSensorRS() {}

    public TemperatureSensorRS(String name, Map<Date, Float> tempHistory) {
        this.name = name;
        this.history = tempHistory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Date, Float> getHistory() {
        return history;
    }

    public void setHistory(Map<Date, Float> history) {
        this.history = history;
    }
}
