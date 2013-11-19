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

import co.kr.daesung.app.center.api.web.aops.AuthKeyCheckAdvice;
import co.kr.daesung.app.center.api.web.configs.ControllerConfiguration;
import co.kr.daesung.app.center.api.web.configs.SecurityConfiguration;
import co.kr.daesung.app.center.api.web.vos.ResultData;
import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

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
    public void testGetSiDoList() throws Exception {
        MvcResult result = mvc.perform(get(AddressSearchController.API_ADDRESS_SIDO_LIST)
                .param("key", authKey)
                .header("client", clientApp))
                .andExpect(status().isOk())
                .andReturn();
        String contentString = result.getResponse().getContentAsString();
        ResultData resultData = objectMapper.readValue(contentString, ResultData.class);
        assertThat(resultData.isOk(), is(true));
    }

    @Test
    public void testSearchByJibeonWithApplication() throws Exception {
        MvcResult result = mvc.perform(get(AddressSearchController.API_ADDRESS_SEARCH)
                .param("jibeonName", "신도림")
                .param("mergeJibeon", "true")
                .param("pageIndex", "0")
                .param("pageSize", "10")
                .param("key", authKey)
                .header("client", clientApp))
                .andExpect(status().isOk())
                .andReturn();
        String contentString = result.getResponse().getContentAsString();
        System.out.println(contentString);
        ResultData resultData = objectMapper.readValue(contentString, ResultData.class);
        assertThat(resultData.isOk(), is(true));
    }

    @Test
    public void testSearchByJibeonWithHttpClient() throws Exception {
        MvcResult result = mvc.perform(get(AddressSearchController.API_ADDRESS_SEARCH)
                .param("jibeonName", "신도림")
                .param("mergeJibeon", "true")
                .param("pageIndex", "0")
                .param("pageSize", "10")
                .param("key", authKey)
                .header("Referer", clientWeb))
                .andExpect(status().isOk())
                .andReturn();
        String contentString = result.getResponse().getContentAsString();
        System.out.println(contentString);
        ResultData resultData = objectMapper.readValue(contentString, ResultData.class);
        assertThat(resultData.isOk(), is(true));
    }


    @Test
    public void testSearchByJibeonWithNoKey() throws Exception {
        MvcResult result = mvc.perform(get(AddressSearchController.API_ADDRESS_SEARCH)
                                .param("jibeonName", "신도림")
                                .param("mergeJibeon", "true")
                                .param("pageIndex", "0")
                                .param("pageSize", "10"))
                           .andExpect(status().isOk())
                           .andReturn();
        String contentString = result.getResponse().getContentAsString();
        ResultData resultData = objectMapper.readValue(contentString, ResultData.class);
        assertThat(resultData.isOk(), is(false));
        assertThat(resultData.getMessage(), is(AuthKeyCheckAdvice.NOT_AUTHORIZED));
        System.out.println(result.getResponse().getContentAsString());

    }

    @Test
    public void testSearchByJibeonUsingJsonpWithNoKey() throws Exception {
        MvcResult result = mvc.perform(get(AddressSearchController.API_ADDRESS_SEARCH)
                .param("jibeonName", "신도림")
                .param("mergeJibeon", "true")
                .param("pageIndex", "0")
                .param("pageSize", "10")
                .param("callback", "CALL_BACK_METHOD"))
                .andExpect(status().isOk())
                .andReturn();
        String contents = result.getResponse().getContentAsString();
        assertThat(contents.startsWith("CALL_BACK_METHOD"),is(true));
    }

    @Test
    public void testSearchByRoad() throws Exception {
        MvcResult result = mvc.perform(get(AddressSearchController.API_ADDRESS_SEARCH)
                .param("roadName", "지봉로")
                .param("sidoNumber", "11")
                .param("merge", "true")
                .param("pageIndex", "0")
                .param("pageSize", "10")
                .param("key", authKey)
                .header("client", clientApp))
                .andExpect(status().isOk())
                .andReturn();
        String contentString = result.getResponse().getContentAsString();
        System.out.println(contentString);
        ResultData resultData = objectMapper.readValue(contentString, ResultData.class);
        assertThat(resultData.isOk(), is(true));
    }

    @Test
    public void testSearchByBuildingName() throws Exception {
        MvcResult result = mvc.perform(get(AddressSearchController.API_ADDRESS_SEARCH)
                .param("sidoNumber", "11")
                .param("buildingName", "푸르지오")
                .param("pageIndex", "0")
                .param("pageSize", "10")
                .param("key", authKey)
                .header("client", clientApp))
                .andExpect(status().isOk())
                .andReturn();
        String contentString = result.getResponse().getContentAsString();
        System.out.println(contentString);
        ResultData resultData = objectMapper.readValue(contentString, ResultData.class);
        assertThat(resultData.isOk(), is(true));
    }

    @Test
    public void testGetSiGunGuList() throws Exception {
        MvcResult result = mvc.perform(get(AddressSearchController.API_ADDRESS_SIGUNGU_LIST)
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
