package co.kr.daesung.app.center.api.web.controllers.apis;

import co.kr.daesung.app.center.api.web.aops.ApiKeyRequired;
import co.kr.daesung.app.center.api.web.aops.Jsonp;
import co.kr.daesung.app.center.api.web.aops.ResultDataFormat;
import co.kr.daesung.app.center.api.web.vos.AddressItem;
import co.kr.daesung.app.center.domain.entities.address.SiDo;
import co.kr.daesung.app.center.domain.entities.address.SiGunGu;
import co.kr.daesung.app.center.domain.entities.address.support.BaseAddress;
import co.kr.daesung.app.center.domain.entities.auth.ApiMethod;
import co.kr.daesung.app.center.domain.services.AddressSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 1:13 AM
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class AddressSearchController {
    @Autowired
    private AddressSearchService service;

    @RequestMapping(value = {ApiMethod.API_ADDRESS_SIDO_LIST, ApiMethod.API_ADDRESS_SIDO_LIST_OLD})
    @ResultDataFormat
    @Jsonp
    @ApiKeyRequired
    public Object getSiDoList(HttpServletRequest request, HttpServletResponse response) {
        final List<SiDo> siDoList = service.getSiDoList();
        List<Map<String, String>> result =new ArrayList<>();
        for(SiDo sido : siDoList) {
            Map<String, String> item = new Hashtable<>();
            item.put("SiDoNumber", sido.getSidoNumber());
            item.put("SiDoName", sido.getSidoName());
            result.add(item);
        }
        return result;
    }


    @RequestMapping(value = {ApiMethod.API_ADDRESS_SEARCH_BY_JIBEON, ApiMethod.API_ADDRESS_SEARCH_BY_JIBEON_OLD})
    @ResultDataFormat
    @Jsonp
    @ApiKeyRequired
    public Object searchByJibeon(HttpServletRequest request, HttpServletResponse response,
                               String jibeonName, boolean mergeJibeon, int pageIndex, int pageSize) throws IOException {

        final List<BaseAddress> addresses = service.searchByJibeon(jibeonName, mergeJibeon, pageIndex, pageSize);
        return convertBaseAddressesToSearchAddressResults(addresses, mergeJibeon);
    }

    @RequestMapping(value = {ApiMethod.API_ADDRESS_SEARCH_BY_ROAD, ApiMethod.API_ADDRESS_SEARCH_BY_ROAD_OLD})
    @ResultDataFormat
    @Jsonp
    @ApiKeyRequired
    public Object searchByRoad(HttpServletRequest request, HttpServletResponse response,
                               String sidoNumber, boolean merge, String roadName, int pageIndex, int pageSize) {
        final List<BaseAddress> baseAddresses = service.searchByRoad(sidoNumber, roadName, merge, pageIndex, pageSize);
        return convertBaseAddressesToSearchAddressResults(baseAddresses, merge);
    }

    @RequestMapping(value = {ApiMethod.API_ADDRESS_SEARCH_BY_BUILDING, ApiMethod.API_ADDRESS_SEARCH_BY_BUILDING_OLD})
    @ResultDataFormat
    @Jsonp
    @ApiKeyRequired
    public Object searchByBuildingName(HttpServletRequest request, HttpServletResponse response,
                                       String sidoNumber, String buildingName, int pageIndex, int pageSize) {
        if(buildingName == null || buildingName.length() < 3) {
            throw new IllegalArgumentException("빌딩 이름 검색어를 3글자 이상 넣어주세요.");
        }
        final List<BaseAddress> addresses = service.searchByBuilding(sidoNumber, buildingName, pageIndex, pageSize);
        return convertBaseAddressesToSearchAddressResults(addresses, false);
    }

    @RequestMapping(value = {ApiMethod.API_ADDRESS_SIGUNGU_LIST, ApiMethod.API_ADDRESS_SIGUNGU_LIST_OLD})
    @ResultDataFormat
    @Jsonp
    @ApiKeyRequired
    public Object getSiGunGuList(HttpServletRequest request, HttpServletResponse response, String sidoNumber) {
        final List<SiGunGu> siGunGuList = service.getSiGunGuList(sidoNumber);
        List<Map<String, String>> result = new ArrayList<>();
        for(SiGunGu siGunGu : siGunGuList) {
            Map<String, String> item = new Hashtable<>();
            item.put("SiDoNumber", siGunGu.getSido().getSidoNumber());
            item.put("SiDoName", siGunGu.getSido().getSidoName());
            item.put("SiGunGuNumber", siGunGu.getSiGunGuNumber());
            item.put("SiGunGuName", siGunGu.getSiGunGuName());
            result.add(item);
        }
        return result;
    }

    private List<AddressItem> convertBaseAddressesToSearchAddressResults(List<BaseAddress> baseAddresses, boolean mergeJibeon) {
        List<AddressItem> results = new ArrayList<>();
        for(BaseAddress address : baseAddresses) {
            results.add(new AddressItem(address, mergeJibeon));
        }
        return results;
    }
}
