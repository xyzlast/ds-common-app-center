package co.kr.daesung.app.center.api.web.controllers;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/22/13
 * Time: 9:18 AM
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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DomainConfiguration.class, SecurityConfiguration.class, ControllerConfiguration.class })
@WebAppConfiguration
class LoginControllerTest2 {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static final String USER_NAME = "ykyoon";
    private static final String PASSWORD = "1234";

    @Before
    public void setUp() throws Exception {
        mvc = AuthorizedControllerHelper.getSecurityAppliedMockMvc(context);
        assertThat(mvc, is(not(nullValue())));
    }

    @Test
    public void getLoginStatus() throws Exception {
        MvcResult result1 = mvc.perform(get(LoginController.API_LOGIN))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ResultData r1 = objectMapper.readValue(result1.getResponse().getContentAsString(), ResultData.class);
        assertThat(r1.isOk(), is(true));
        Map<String, Object> loginStatus1 = (Map<String, Object>) r1.getData();
        assertThat((Boolean) loginStatus1.get("authenticated"), is(false));

        MockHttpSession session = new MockHttpSession();
        mvc.perform(post(LoginController.API_LOGIN)
                .param("username", USER_NAME).param("password", PASSWORD).session(session))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult result2 = mvc.perform(get(LoginController.API_LOGIN))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ResultData r2 = objectMapper.readValue(result2.getResponse().getContentAsString(), ResultData.class);
        assertThat(r2.isOk(), is(true));
        Map<String, Object> loginStatus2 = (Map<String, Object>) r2.getData();
        assertThat((Boolean) loginStatus2.get("authenticated"), is(false));
    }

    @Test
    public void login() throws Exception {
        mvc.perform(get(LoginController.API_LOGIN))
                .andExpect(status().isOk())
                .andDo(print());

        MockHttpSession session = new MockHttpSession();
        MvcResult mvcResult = mvc.perform(post(LoginController.API_LOGIN)
                .param("username", USER_NAME).param("password", PASSWORD).session(session))
                .andExpect(status().isOk())
                .andReturn();

        ResultData r = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResultData.class);
        assertThat(r.isOk(), is(true));
        Map<String, Object> loginStatus = (Map<String, Object>) r.getData();
        assertThat((Boolean) loginStatus.get("authenticated"), is(true));

        MvcResult apiResult = mvc.perform(get(ApiKeyController.API_API_KEY_LIST)
                .param("pageIndex", "0").param("pageSize", "10").session(session))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }


}
