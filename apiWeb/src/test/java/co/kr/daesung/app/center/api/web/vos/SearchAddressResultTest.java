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
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

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
        SearchAddressResult r = new SearchAddressResult(baseAddress, true);
        assertThat(r.getJibeonAddress(), is(JIBEON_ADDRESS_WITH_MERGE));
        assertThat(r.getPostCode(), is(POST_CODE));
        assertThat(r.getRoadAddress(), is(ROAD_ADDRESS));
    }

    @Test
    public void getSearchAddressResultWithNoMerge() {
        SearchAddressResult r = new SearchAddressResult(baseAddress, false);
        assertThat(r.getJibeonAddress(), is(JIBEON_ADDRESS));
        assertThat(r.getPostCode(), is(POST_CODE));
        assertThat(r.getRoadAddress(), is(ROAD_ADDRESS));
    }
}
