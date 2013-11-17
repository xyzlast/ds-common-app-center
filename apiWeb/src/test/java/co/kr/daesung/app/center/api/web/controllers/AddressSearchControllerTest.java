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

import co.kr.daesung.app.center.api.web.configs.ControllerConfiguration;
import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DomainConfiguration.class, ControllerConfiguration.class })
@WebAppConfiguration
public class AddressSearchControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = webAppContextSetup(context).build();
        assertThat(mvc, is(not(nullValue())));
    }

    @Test
    public void testSearchByJibeon() throws Exception {
        MvcResult result = mvc.perform(get("/api/searchByJibeon")
                                .param("jibeonName", "신도림")
                                .param("mergeJibeon", "true")
                                .param("pageIndex", "0")
                                .param("pageSize", "10"))
                           .andExpect(status().isOk())
                           .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testSearchByJibeonUsingJsonp() throws Exception {
        MvcResult result = mvc.perform(get("/api/searchByJibeon")
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
}
