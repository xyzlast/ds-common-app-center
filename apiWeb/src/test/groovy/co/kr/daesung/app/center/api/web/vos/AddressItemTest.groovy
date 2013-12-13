package co.kr.daesung.app.center.api.web.vos

import co.kr.daesung.app.center.domain.entities.address.support.BaseAddress
import spock.lang.Specification

/**
 * Created by ykyoon on 12/12/13.
 */
class AddressItemTest extends Specification {
    private static final String JIBEON_ADDRESS_WITH_MERGE = "JIBEON_ADDRESS_1"
    private static final String JIBEON_ADDRESS = "JIBEON_ADDRESS_2"
    private static final String ROAD_ADDRESS = "ROAD_ADDRESS"
    private static final String POST_CODE= "POST_CODE"

    BaseAddress baseAddress

    def setup() {
        baseAddress = Mock(BaseAddress)
        baseAddress.toJibeonAddress(true) >> JIBEON_ADDRESS_WITH_MERGE
        baseAddress.toJibeonAddress(false) >> JIBEON_ADDRESS
        baseAddress.toRoadAddress() >> ROAD_ADDRESS
        baseAddress.postCode >> POST_CODE
    }

    def 'Merge된 Address의 표시'() {
        expect:
        AddressItem item = new AddressItem(baseAddress, merged)
        item.getJibeonAddress() == jibeonAddress
        item.getRoadAddress() == roadAddress

        where:
        merged | jibeonAddress | roadAddress
        true | JIBEON_ADDRESS_WITH_MERGE | ROAD_ADDRESS
        false | JIBEON_ADDRESS | ROAD_ADDRESS
    }
}
