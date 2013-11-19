package co.kr.daesung.app.center.api.web.controllers;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 6:09 PM
 * To change this template use File | Settings | File Templates.
 */
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import co.kr.daesung.app.center.api.web.configs.ControllerConfiguration;
import co.kr.daesung.app.center.api.web.configs.SecurityConfiguration;
import co.kr.daesung.app.center.api.web.vos.ResultData;
import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.entities.auth.User;
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

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DomainConfiguration.class, SecurityConfiguration.class, ControllerConfiguration.class })
@WebAppConfiguration
public class AuthControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    private static final String USER_NAME = "ykyoon";
    private static final String PASSWORD = "1234";
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        mvc = AuthorizedControllerHelper.getSecurityAppliedMockMvc(context);
    }

    @Test
    public void getUserInfo() throws Exception {
        String authString = AuthorizedControllerHelper
                .getDigestAuthenticateion(mvc, USER_NAME, PASSWORD, AuthController.API_AUTH_USERINFO, "GET");
        MvcResult result = mvc.perform(get(AuthController.API_AUTH_USERINFO).header(AuthorizedControllerHelper.AUTH_HEADER, authString))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ResultData r = objectMapper.readValue(result.getResponse().getContentAsString(), ResultData.class);
        assertThat(r.isOk(), is(true));
        assertThat(r.getData(), is(not(nullValue())));
    }
}
