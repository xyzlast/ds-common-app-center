package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.constants.SidoEnum;
import co.kr.daesung.app.center.domain.entities.address.EupMyeonDongRi;
import co.kr.daesung.app.center.domain.entities.address.Road;
import co.kr.daesung.app.center.domain.entities.address.SiDo;
import co.kr.daesung.app.center.domain.entities.address.SiGunGu;
import co.kr.daesung.app.center.domain.entities.address.support.BaseAddress;
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

import java.util.List;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DomainConfiguration.class })
public class AddressSearchServiceImplTest {
    @Autowired
    private AddressSearchService addressService;

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
        for(SidoEnum sido : SidoEnum.values()) {
            String sidoNumber = Integer.valueOf(sido.getValue()).toString();
            final List<SiGunGu> siGunGuList = addressService.getSiGunGuList(sidoNumber);
            assertThat(siGunGuList.size(), is(not(0)));
        }
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
    public void testSearchByJibeon() throws Exception {
        List<BaseAddress> addresses = addressService.searchByJibeon("신도림동", false, 0, 999);
        for(BaseAddress address : addresses) {
            System.out.println(address.toString());
        }
    }

    @Test
    public void testGetRoadNumbers() throws Exception {
        final List<Road> roads = addressService.getRoadNumbers("역곡");
        assertThat(roads.size(), is(not(0)));
    }

    @Test
    public void testSearchByRoad() throws Exception {
        String roadName1 = "지봉로 1";
        final List<BaseAddress> baseAddresses1 = addressService.searchByRoad("11", roadName1, true, 0, Integer.MAX_VALUE);
        assertThat(baseAddresses1.size(), is(not(0)));
    }

    @Test
    public void testSearchByBuilding() throws Exception {
        String buildingName = "레미안";
        SidoEnum sido = SidoEnum.SEOUL;
        String sidoNumber = Integer.valueOf(sido.getValue()).toString();
        final List<BaseAddress> baseAddresses = addressService.searchByBuilding(sidoNumber, buildingName, 0, 999);
        assertThat(baseAddresses.size(), is(not(0)));
        for(BaseAddress address : baseAddresses) {
            assertThat(address.getBuildingName().contains(buildingName), is(true));
            System.out.println(address);
        }
    }

    @Test
    public void testSearchByBuilding2() throws Exception {
        String buildingName = "레미안";
        final List<BaseAddress> baseAddresses = addressService.searchByBuilding(buildingName);
        assertThat(baseAddresses.size(), is(not(0)));
        for(BaseAddress address : baseAddresses) {
            assertThat(address.getBuildingName().contains(buildingName), is(true));
            System.out.println(address);
        }
    }

    @Test
    public void testInsertAddressByFileData() throws Exception {

    }
}
