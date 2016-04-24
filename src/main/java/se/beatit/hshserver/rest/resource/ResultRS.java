package se.beatit.hshserver.rest.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by stefan on 4/21/16.
 */
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultRS  implements Serializable  {

    @JsonIgnore
    public final static int RESULT_OK = 0;

    private String message;
    private int status;

    public ResultRS(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
