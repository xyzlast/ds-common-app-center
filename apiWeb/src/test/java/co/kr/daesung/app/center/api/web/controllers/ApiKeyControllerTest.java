package co.kr.daesung.app.center.api.web.controllers;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 7:12 PM
 * To change this template use File | Settings | File Templates.
 */
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import co.kr.daesung.app.center.api.web.configs.ControllerConfiguration;
import co.kr.daesung.app.center.api.web.configs.SecurityConfiguration;
import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { DomainConfiguration.class, SecurityConfiguration.class, ControllerConfiguration.class})
public class ApiKeyControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = AuthorizedControllerHelper.getSecurityAppliedMockMvc(context);
        //mvc = webAppContextSetup(context).build();
        assertThat(mvc, is(not(nullValue())));
    }

    @Test
    public void getTempDataWithNoAuth() throws Exception {
        mvc.perform(get("/api/apiKey/getTempData"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getTempDataWithAuth() throws Exception {
        mvc.perform(get("/api/apiKey/getTempData")
                .header(AuthorizedControllerHelper.AUTH_HEADER, AuthorizedControllerHelper.getBasicAuthHeaderValue("ykyoon", "1234")))
                .andExpect(status().isOk());
    }

    @Test
    public void testGenerateKey() throws Exception {
        String clientRequest = AuthorizedControllerHelper.getDigestAuthenticateion(mvc, "ykyoon", "1234", "/api/apiKey/generate", "GET");
        MvcResult result = mvc.perform(get("/api/apiKey/generate")
                .header(AuthorizedControllerHelper.AUTH_HEADER, clientRequest))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        mvc.perform(get("/api/apiKey/generate")
                .header(AuthorizedControllerHelper.AUTH_HEADER, clientRequest))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        mvc.perform(get("/api/apiKey/generate")
                .header(AuthorizedControllerHelper.AUTH_HEADER, clientRequest))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        mvc.perform(get("/api/apiKey/generate")
                .header(AuthorizedControllerHelper.AUTH_HEADER, clientRequest))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }


    @Test
    public void testDelteKey() throws Exception {
        String authValue = AuthorizedControllerHelper.getBasicAuthHeaderValue("ykyoon", "662a5aae230dd1be88bf1b8dbdb38dc0");
        MvcResult result = mvc.perform(delete("/api/apiKey/delete")
                .param("apiKeyId", "ABC")
                .header(AuthorizedControllerHelper.AUTH_HEADER, authValue))
                .andExpect(status().isOk())
                .andReturn();
        String contentString = result.getResponse().getContentAsString();
        System.out.println(contentString);
    }

    @Test
    public void testGetApiKeyList() throws Exception {
        String authValue = AuthorizedControllerHelper.getBasicAuthHeaderValue("ykyoon", "1234");
        MvcResult result = mvc.perform(put("/api/apiKey/apiKeyList")
                                        .param("pageIndex", "0")
                                        .param("pageSize", "10")
                                        .header(AuthorizedControllerHelper.AUTH_HEADER, authValue))
                            .andDo(MockMvcResultHandlers.print())
                            .andExpect(status().isOk())
                            .andReturn();
        String contentString = result.getResponse().getContentAsString();
        System.out.println(contentString);
    }
}
