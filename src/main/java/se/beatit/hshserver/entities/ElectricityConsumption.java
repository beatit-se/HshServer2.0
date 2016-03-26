package se.beatit.hshserver.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by stefan on 3/13/16.
 */
@Entity
@Table(name="electricityconsumption")
@XmlRootElement
public class ElectricityConsumption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="home_id")
    private Home home;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fromDate;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date toDate;

    private long whUsed;

    public ElectricityConsumption () {}

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

    public long getWhUsed() {
        return whUsed;
    }

    public void setWhUsed(long whUsed) {
        this.whUsed = whUsed;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElectricityConsumption that = (ElectricityConsumption) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
