package se.beatit.hshserver.rest.domain;

import se.beatit.hshserver.entities.Home;

import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by stefan on 3/20/16.
 */
@XmlRootElement
public class KWhHistoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Home home;

    private Date timestamp;

    private long watts;

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public long getWatts() {
        return watts;
    }

    public void setWatts(long watts) {
        this.watts = watts;
    }


}
