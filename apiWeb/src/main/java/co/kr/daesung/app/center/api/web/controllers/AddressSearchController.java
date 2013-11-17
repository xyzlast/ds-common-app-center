package co.kr.daesung.app.center.api.web.controllers;

import co.kr.daesung.app.center.api.web.aops.Jsonp;
import co.kr.daesung.app.center.api.web.aops.ResultDataFormat;
import co.kr.daesung.app.center.api.web.vos.SearchAddressResult;
import co.kr.daesung.app.center.domain.entities.address.support.BaseAddress;
import co.kr.daesung.app.center.domain.services.AddressSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 1:13 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class AddressSearchController {
    @Autowired
    private AddressSearchService service;
    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/api/searchByJibeon")
    @ResponseBody
    @ResultDataFormat
    @Jsonp
    public Object searchByJibeon(HttpServletRequest request, HttpServletResponse response,
                               String jibeonName, boolean mergeJibeon, int pageIndex, int pageSize) throws IOException {

        final List<BaseAddress> addresses = service.searchByJibeon(jibeonName, mergeJibeon, pageIndex, pageSize);
        List<SearchAddressResult> results = new ArrayList<>();
        for(BaseAddress address : addresses) {
            results.add(new SearchAddressResult(address, mergeJibeon));
        }
        return results;
    }
}
