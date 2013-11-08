package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.entities.EupMyeonDongRi;
import co.kr.daesung.app.center.domain.entities.SiDo;
import co.kr.daesung.app.center.domain.entities.support.BaseAddress;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/7/13
 * Time: 4:17 PM
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DomainConfiguration.class })
public class AddressServiceImplTest {
    @Autowired
    private AddressService addressService;

    @Before
    public void setUp() {
        assertThat(addressService, is(not(nullValue())));
    }

    @Test
    public void testGetSiDoList() throws Exception {
        final List<SiDo> siDoList = addressService.getSiDoList();
        assertThat(siDoList.size(), is(not(0)));
    }

    @Test
    public void testGetSiGunGuList() throws Exception {

    }

    @Test
    public void testGetEupMyeonDongRiNumbers() throws Exception {
        final List<EupMyeonDongRi> eupMyeonDongRiList = addressService.getEupMyeonDongRiNumbers("서초1동");
        assertThat(eupMyeonDongRiList.size(), is(not(0)));
        for(EupMyeonDongRi eup : eupMyeonDongRiList) {
            assertThat(eup.getHaengJungDongName().contains("서초1동"), is(true));
        }
    }

    @Test
    public void testExtractSearchText() throws Exception {
        String sample1 = "구로동 245 번지";
        Matcher matcher1 = extractSearchText(sample1);
        assertThat(matcher1.find(), is(true));
        assertThat(matcher1.group(1), is("구로동"));
        assertThat(matcher1.group(2), is("245"));

        String sample2 = "서초1동";
        Matcher matcher2 = extractSearchText(sample2);
        assertThat(matcher2.find(), is(false));

        String sample3 = "잠실동 624";
        Matcher matcher3 = extractSearchText(sample3);
        assertThat(matcher3.find(), is(true));
        assertThat(matcher3.group(1), is("잠실동"));
        assertThat(matcher3.group(2), is("624"));
    }

    private Matcher extractSearchText(String searchText) {
        String regex = "([^-\\s]*)\\s(\\d*)[^\\d]*";
        Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(searchText);
        return matcher;
    }

    @Test
    public void testSearchByJibeon() throws Exception {
        List<BaseAddress> addresses = addressService.searchByJibeon("신도림동", false, 0, 999);
        for(BaseAddress address : addresses) {
            System.out.println(address.toString());
        }
    }

    @Test
    public void testGetRoadNumbers() throws Exception {

    }

    @Test
    public void testSearchByRoad() throws Exception {

    }

    @Test
    public void testSearchAddressBySiDoSiGunGuRoadList() throws Exception {

    }

    @Test
    public void testSearchByBuilding() throws Exception {

    }

    @Test
    public void testInsertAddressByFileData() throws Exception {

    }
}
