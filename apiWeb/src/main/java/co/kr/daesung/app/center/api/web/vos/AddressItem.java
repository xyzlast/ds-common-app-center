package co.kr.daesung.app.center.api.web.vos;

import co.kr.daesung.app.center.domain.entities.address.support.BaseAddress;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 1:42 AM
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class AddressItem implements Serializable {
    @JsonProperty(value = "PostCode")
    private String postCode;
    @JsonProperty(value = "JibeonAddress")
    private String jibeonAddress;
    @JsonProperty(value = "RoadAddress")
    private String roadAddress;

    public AddressItem() {

    }

    public AddressItem(BaseAddress baseAddress, boolean merge) {
        postCode = baseAddress.getDisplayPostCode();
        jibeonAddress = baseAddress.toJibeonAddress(merge);
        roadAddress = baseAddress.toRoadAddress();
    }
}
