package co.kr.daesung.app.center.api.web.controllers.apis;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/20/13
 * Time: 9:31 AM
 * To change this template use File | Settings | File Templates.
 */
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import co.kr.daesung.app.center.api.web.aops.ApiKeyRequiredAdvice;
import co.kr.daesung.app.center.api.web.configs.ControllerConfiguration;
import co.kr.daesung.app.center.api.web.configs.SecurityConfiguration;
import co.kr.daesung.app.center.api.web.controllers.AuthorizedControllerHelper;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DomainConfiguration.class, SecurityConfiguration.class, ControllerConfiguration.class})
@WebAppConfiguration
public class MessageControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    private static final String USER_NAME = "ykyoon";
    private static final String PASSWORD = "1234";
    private static final String AUTH_KEY = ApiKeyRequiredAdvice.TEST_KEY;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        mvc = AuthorizedControllerHelper.getSecurityAppliedMockMvc(context);
        assertThat(mvc, is(not(nullValue())));
    }

    @Test
    public void addCrewMessage() throws Exception {
        addCrewMessage(ApiMethod.API_MESSAGE_SEND_CREW);
        addCrewMessage(ApiMethod.API_MESSAGE_SEND_CREW_OLD);
    }

    private void addCrewMessage(String url) throws Exception {
        MockHttpServletRequestBuilder request = post(url)
                .param("key", AUTH_KEY)
                .param("fromUser", "201105010")
                .param("toUser", "201105010")
                .param("message", "Crew Message")
                .param("linkUrl", "http://www.daum.net")
                .param("force", Boolean.FALSE.toString())
                .header("Origin", "http://github.com");
        MvcResult mvcResult = mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        ResultData r = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResultData.class);
        assertThat(r.getMessage(), r.isOk(), is(true));
    }

    @Test
    public void addNLeaderMessage() throws Exception {
        addNLeaderMessage(ApiMethod.API_MESSAGE_SEND_NLEADER);
        addNLeaderMessage(ApiMethod.API_MESSAGE_SEND_NLEADER_OLD);
    }

    private void addNLeaderMessage(String url) throws Exception {
        MockHttpServletRequestBuilder request = post(url)
                .param("key", AUTH_KEY)
                .param("fromUser", "201105010")
                .param("toUser", "201105010")
                .param("title", "TITLE")
                .param("content", "http://www.daum.net")
                .param("force", Boolean.FALSE.toString())
                .header("Origin", "http://github.com");
        MvcResult mvcResult = mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        ResultData r = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResultData.class);
        assertThat(r.getMessage(), r.isOk(), is(true));
    }

    @Test
    public void getCrewMessageList() throws Exception {
        String url = ApiMethod.API_MESSAGE_CREW_LIST;
        MvcResult result = mvc.perform(get(url)
                                .param("key", AUTH_KEY)
                                .param("pageIndex", "0")
                                .param("pageSize", "100"))
                            .andDo(print())
                            .andExpect(status().isOk())
                            .andReturn();
        ResultData r = objectMapper.readValue(result.getResponse().getContentAsString(), ResultData.class);
        assertThat(r.getMessage(), r.isOk(), is(true));
    }

    @Test
    public void getNLeaderMessageList() throws Exception {
        String url = ApiMethod.API_MESSAGE_NLEADER_LIST;
        MvcResult result = mvc.perform(get(url)
                .param("key", AUTH_KEY)
                .param("pageIndex", "0")
                .param("pageSize", "100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ResultData r = objectMapper.readValue(result.getResponse().getContentAsString(), ResultData.class);
        assertThat(r.getMessage(), r.isOk(), is(true));
    }
}
