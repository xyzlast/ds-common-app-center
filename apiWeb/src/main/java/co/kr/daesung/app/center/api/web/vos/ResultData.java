package co.kr.daesung.app.center.api.web.vos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 2:39 AM
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class ResultData {
    @JsonProperty(value = "IsOK")
    private boolean ok;
    @JsonProperty(value = "Message")
    private String message;
    @JsonProperty(value = "Data")
    private Object data;

    public ResultData(Object data) {
        ok = true;
        message = "Execute api result ok";
        this.data = data;
    }

    public ResultData(Exception ex) {
        ok = false;
        message = ex.getMessage();
        data = null;
    }
}
