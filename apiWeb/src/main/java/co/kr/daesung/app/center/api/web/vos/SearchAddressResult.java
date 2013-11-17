package co.kr.daesung.app.center.api.web.vos;

import co.kr.daesung.app.center.domain.entities.address.support.BaseAddress;
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
public class SearchAddressResult implements Serializable {
    private String PostCode;
    private String JibeonAddress;
    private String RoadAddress;

    public SearchAddressResult() {

    }

    public SearchAddressResult(BaseAddress baseAddress, boolean merge) {
        PostCode = baseAddress.getDisplayPostCode();
        JibeonAddress = baseAddress.toJibeonAddress(merge);
        RoadAddress = baseAddress.toRoadAddress();
    }
}
