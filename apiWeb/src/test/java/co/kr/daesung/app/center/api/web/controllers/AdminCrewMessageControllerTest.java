package co.kr.daesung.app.center.api.web.controllers;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 12/2/13
 * Time: 2:03 PM
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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

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
public class AdminCrewMessageControllerTest {
    public static final String USERNAME = "ykyoon";
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        assertThat(context, is(not(nullValue())));
        mvc = AuthorizedControllerHelper.getSecurityAppliedMockMvc(context);
        session = AuthorizedControllerHelper.buildSecuritySession(context, USERNAME);
    }

    @Test
    public void getCrewMessages() throws Exception {
        mvc.perform(get(AdminCrewMessageController.API_ADMIN_CREW_LIST)
                .param("pageIndex", "0")
                .param("pageSize", "10")
                .session(session))
            .andExpect(status().isOk())
            .andDo(print());
    }
}
