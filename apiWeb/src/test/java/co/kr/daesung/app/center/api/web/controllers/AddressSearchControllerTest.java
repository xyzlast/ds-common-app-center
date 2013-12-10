package co.kr.daesung.app.center.api.web.controllers;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 2:22 AM
 * To change this template use File | Settings | File Templates.
 */
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import co.kr.daesung.app.center.api.web.aops.ApiKeyRequiredAdvice;
import co.kr.daesung.app.center.api.web.configs.ControllerConfiguration;
import co.kr.daesung.app.center.api.web.configs.SecurityConfiguration;
import co.kr.daesung.app.center.api.web.vos.ResultData;
import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.entities.auth.ApiMethod;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DomainConfiguration.class, SecurityConfiguration.class, ControllerConfiguration.class })
@WebAppConfiguration
public class AddressSearchControllerTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mvc;
    private static final String authKey = "02906f80-9fab-4208-a9b0-e031b6a7cfca";
    private static final String clientApp = "testCode";
    private static final String clientWeb = "http://github.com";

    @Before
    public void setUp() {
        mvc = webAppContextSetup(context).build();
        assertThat(mvc, is(not(nullValue())));
    }

    @Test
    public void testNoKeyPassed() throws Exception {
        MvcResult result = mvc.perform(get(ApiMethod.API_ADDRESS_SEARCH_BY_JIBEON)
                .param("jibeonName", "신도림")
                .param("mergeJibeon", "true")
                .param("pageIndex", "0")
                .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andReturn();
        String contentString = result.getResponse().getContentAsString();
        ResultData resultData = objectMapper.readValue(contentString, ResultData.class);
        assertThat(resultData.isOk(), is(false));
        assertThat(resultData.getMessage(), is(ApiKeyRequiredAdvice.NOT_AUTHORIZED));
    }

    @Test
    public void testGetSiDoList() throws Exception {
        MvcResult result = mvc.perform(get(ApiMethod.API_ADDRESS_SIDO_LIST)
                .param("key", authKey)
                .header("client", clientApp))
                .andExpect(status().isOk())
                .andReturn();
        String contentString = result.getResponse().getContentAsString();
        ResultData resultData = objectMapper.readValue(contentString, ResultData.class);
        assertThat(resultData.isOk(), is(true));
    }

    @Test
    public void testSearchByJibeon() throws Exception {
        searchByWebApplication("searchByJibeon",
                ApiMethod.API_ADDRESS_SEARCH_BY_JIBEON,
                ApiMethod.API_ADDRESS_SEARCH_BY_JIBEON_OLD);

        searchByCsApplication("searchByJibeon",
                ApiMethod.API_ADDRESS_SEARCH_BY_JIBEON,
                ApiMethod.API_ADDRESS_SEARCH_BY_JIBEON_OLD);

    }

    @Test
    public void testSearchByRoad() throws Exception {
        searchByWebApplication("searchByRoad",
                ApiMethod.API_ADDRESS_SEARCH_BY_ROAD,
                ApiMethod.API_ADDRESS_SEARCH_BY_ROAD_OLD);
        searchByCsApplication("searchByRoad",
                ApiMethod.API_ADDRESS_SEARCH_BY_ROAD,
                ApiMethod.API_ADDRESS_SEARCH_BY_ROAD_OLD);
    }


    @Test
    public void testSearchByBuilding() throws Exception {
        searchByWebApplication("searchByBuilding",
                ApiMethod.API_ADDRESS_SEARCH_BY_BUILDING,
                ApiMethod.API_ADDRESS_SEARCH_BY_BUILDING_OLD);
        searchByCsApplication("searchByBuilding",
                ApiMethod.API_ADDRESS_SEARCH_BY_BUILDING,
                ApiMethod.API_ADDRESS_SEARCH_BY_BUILDING_OLD);
    }


    private void searchByWebApplication(String methodName, String... urls) throws Exception {
        Map<String, String> headers = new Hashtable<>();
        headers.put("Origin", clientWeb);
        Method method = getClass().getMethod(methodName, String.class, Map.class);
        for(String url : urls) {
            method.invoke(this, new Object[] { url, headers});
        }
    }

    private void searchByCsApplication(String methodName, String... urls) throws Exception {
        Map<String, String> headers = new Hashtable<>();
        headers.put("client", clientApp);
        Method method = getClass().getMethod(methodName, String.class, Map.class);
        for(String url : urls) {
            method.invoke(this, new Object[] { url, headers});
        }
    }

    public void searchByJibeon(String url, Map<String, String> headers) throws Exception {
        MockHttpServletRequestBuilder request = get(url).param("jibeonName", "신도림")
                .param("mergeJibeon", "true")
                .param("pageIndex", "0")
                .param("pageSize", "10")
                .param("key", authKey);
        sendAndCheckResult(request, headers);
    }

    public void searchByRoad(String url, Map<String, String> headers) throws Exception {
        MockHttpServletRequestBuilder request = get(url)
                .param("roadName", "지봉로")
                .param("sidoNumber", "11")
                .param("merge", "true")
                .param("pageIndex", "0")
                .param("pageSize", "10")
                .param("key", authKey);
        sendAndCheckResult(request, headers);
    }

    public void searchByBuilding(String url, Map<String, String> headers) throws Exception {
        final MockHttpServletRequestBuilder request = get(url)
                .param("sidoNumber", "11")
                .param("buildingName", "푸르지오")
                .param("pageIndex", "0")
                .param("pageSize", "10")
                .param("key", authKey);
        sendAndCheckResult(request, headers);
    }

    private void sendAndCheckResult(MockHttpServletRequestBuilder request, Map<String,String> headers) throws Exception {
        sendAndCheckResult(request, headers, false);
        sendAndCheckResult(request, headers, true);
    }

    private void sendAndCheckResult(MockHttpServletRequestBuilder request, Map<String,String> headers,
                                    boolean isJsonp) throws Exception {
        if(isJsonp) {
            request.param("callback", "CALL_BACK_METHOD");
        }
        for(String key : headers.keySet()) {
            request.header(key, headers.get(key));
        }
        MvcResult result = mvc.perform(request).andExpect(status().isOk())
                .andReturn();
        String contentString = result.getResponse().getContentAsString();
        if(!isJsonp) {
            ResultData resultData = objectMapper.readValue(contentString, ResultData.class);
            assertThat(resultData.isOk(), is(true));
        } else {
            assertThat(contentString.startsWith("CALL_BACK_METHOD"),is(true));
        }
    }

    @Test
    public void testGetSiGunGuList() throws Exception {
        MvcResult result = mvc.perform(get(ApiMethod.API_ADDRESS_SIGUNGU_LIST)
                .param("sidoNumber", "11")
                .param("key", authKey)
                .header("client", clientApp))
                .andExpect(status().isOk())
                .andReturn();
        String contentString = result.getResponse().getContentAsString();
        System.out.println(contentString);
        ResultData resultData = objectMapper.readValue(contentString, ResultData.class);
        assertThat(resultData.isOk(), is(true));
    }
}
