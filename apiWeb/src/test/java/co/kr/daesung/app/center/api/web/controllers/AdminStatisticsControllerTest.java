package co.kr.daesung.app.center.api.web.controllers;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 12/4/13
 * Time: 9:13 PM
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

import javax.servlet.http.HttpSession;

import java.util.List;
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
public class AdminStatisticsControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        mvc = AuthorizedControllerHelper.getSecurityAppliedMockMvc(context);
        assertThat(mvc, is(not(nullValue())));
    }


    @Test
    public void getStatisticsForApi() throws Exception {
        MockHttpSession session = AuthorizedControllerHelper.buildSecuritySession(context, "ykyoon");
        mvc.perform(get(AdminStatisticsController.API_ADMIN_STATISTICS_API)).andExpect(status().isUnauthorized());
        MvcResult result = mvc.perform(get(AdminStatisticsController.API_ADMIN_STATISTICS_API)
                .session(session))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        ResultData resultData = objectMapper.readValue(result.getResponse().getContentAsString(), ResultData.class);
        assertThat(resultData.isOk(), is(true));
        assertThat(resultData.getData() instanceof List, is(true));
    }

    @Test
    public void getStatisticsForApiKey() throws Exception {
        MockHttpSession session = AuthorizedControllerHelper.buildSecuritySession(context, "ykyoon");
        mvc.perform(get(AdminStatisticsController.API_ADMIN_STATISTICS_API_KEY)).andExpect(status().isUnauthorized());
        MvcResult result = mvc.perform(get(AdminStatisticsController.API_ADMIN_STATISTICS_API_KEY)
                .param("limit", "10")
                .session(session))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        ResultData resultData = objectMapper.readValue(result.getResponse().getContentAsString(), ResultData.class);
        assertThat(resultData.isOk(), is(true));
        assertThat(resultData.getData() instanceof List, is(true));
        List<Map<String, Object>> data = (List<Map<String, Object>>) resultData.getData();
        assertThat(data.size() <= 10, is(true));
    }
}
