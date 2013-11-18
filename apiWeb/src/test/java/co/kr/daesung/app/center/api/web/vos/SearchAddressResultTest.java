package co.kr.daesung.app.center.api.web.vos;

import co.kr.daesung.app.center.domain.entities.address.support.BaseAddress;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 1:44 AM
 * To change this template use File | Settings | File Templates.
 */
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import org.junit.*;

import static org.mockito.Mockito.*;

public class SearchAddressResultTest {

    private static final String JIBEON_ADDRESS_WITH_MERGE = "JIBEON_ADDRESS_1";
    private static final String JIBEON_ADDRESS = "JIBEON_ADDRESS_2";
    private static final String ROAD_ADDRESS = "ROAD_ADDRESS";
    private static final String POST_CODE= "POST_CODE";
    private BaseAddress baseAddress;

    @Before
    public void setUp() {
        baseAddress = mock(BaseAddress.class);
        stub(baseAddress.toJibeonAddress(true)).toReturn(JIBEON_ADDRESS_WITH_MERGE);
        stub(baseAddress.toJibeonAddress(false)).toReturn(JIBEON_ADDRESS);
        stub(baseAddress.toRoadAddress()).toReturn(ROAD_ADDRESS);
        stub(baseAddress.getDisplayPostCode()).toReturn(POST_CODE);
    }

    @Test
    public void getSearchAddressResultWithMerge() {
        AddressItem r = new AddressItem(baseAddress, true);
        assertThat(r.getJibeonAddress(), is(JIBEON_ADDRESS_WITH_MERGE));
        assertThat(r.getPostCode(), is(POST_CODE));
        assertThat(r.getRoadAddress(), is(ROAD_ADDRESS));
    }

    @Test
    public void getSearchAddressResultWithNoMerge() {
        AddressItem r = new AddressItem(baseAddress, false);
        assertThat(r.getJibeonAddress(), is(JIBEON_ADDRESS));
        assertThat(r.getPostCode(), is(POST_CODE));
        assertThat(r.getRoadAddress(), is(ROAD_ADDRESS));
    }
}
