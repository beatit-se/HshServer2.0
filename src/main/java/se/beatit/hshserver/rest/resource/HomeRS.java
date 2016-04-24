package se.beatit.hshserver.rest.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import se.beatit.hshserver.entities.Home;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by stefan on 3/25/16.
 */
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeRS implements Serializable {
    private String name;
    private TemperaturesRS currentTemperatures;
    private Long currentElectricityUsage;
    private Date timestamp = new Date();

    private Map<Date, Long> electricityConsumptionHistory;
    private List<TemperaturesRS> temperatureHistory;

    @JsonIgnore
    public final static String DATE_FORMAT = "yyyy-MM-dd";

    public HomeRS() {
    }

    public HomeRS(Home home) {
        name = home.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TemperaturesRS getCurrentTemperatures() {
        return currentTemperatures;
    }

    public void setCurrentTemperatures(TemperaturesRS currentTemperatures) {
        this.currentTemperatures = currentTemperatures;
    }

    public Long getCurrentElectricityUsage() {
        return currentElectricityUsage;
    }

    public void setCurrentElectricityUsage(Long currentElectricityUsage) {
        this.currentElectricityUsage = currentElectricityUsage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Map<Date, Long> getElectricityConsumptionHistory() {
        return electricityConsumptionHistory;
    }

    public void setElectricityConsumptionHistory(Map<Date, Long> electricityConsumptionHistory) {
        this.electricityConsumptionHistory = electricityConsumptionHistory;
    }

    public List<TemperaturesRS> getTemperatureHistory() {
        return temperatureHistory;
    }

    public void setTemperatureHistory(List<TemperaturesRS> temperatureHistory) {
        this.temperatureHistory = temperatureHistory;
    }
}
