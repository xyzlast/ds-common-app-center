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
import co.kr.daesung.app.center.api.web.vos.ResultData;
import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.entities.auth.AcceptProgram;
import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import co.kr.daesung.app.center.domain.services.ApiKeyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { DomainConfiguration.class, SecurityConfiguration.class, ControllerConfiguration.class})
public class ApiKeyControllerTest {

    private static final String USER_ID = "ykyoon";
    private static final String PASSWORD = "1234";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Autowired
    private ApiKeyService apiKeyService;
    private String apiKeyId;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    @Transactional
    public void setUp() throws Exception {
        mvc = AuthorizedControllerHelper.getSecurityAppliedMockMvc(context);
        assertThat(mvc, is(not(nullValue())));
        apiKeyId = apiKeyService.generateNewKey(USER_ID).getId();
    }

    @After
    @Transactional
    public void tearDown() throws Exception {
        apiKeyService.deleteKey(USER_ID, apiKeyId);
    }

    @Test
    public void getTempDataWithNoAuth() throws Exception {
        mvc.perform(get(ApiKeyController.GET_TEMP_DATA))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getTempDataWithAuth() throws Exception {
        String uri = ApiKeyController.GET_TEMP_DATA;
        String digestAuthenticateion = AuthorizedControllerHelper.buildDigestAuthenticateion(mvc, "ykyoon", "1234", uri, "GET");
        mvc.perform(get(uri)
                .header(AuthorizedControllerHelper.AUTH_HEADER, digestAuthenticateion))
                .andExpect(status().isOk());
    }

    @Test
    public void testGenerateKey() throws Exception {
        String digestAuthenticateion = AuthorizedControllerHelper
                .buildDigestAuthenticateion(mvc, "ykyoon", "1234", ApiKeyController.API_API_KEY_GENERATE, "PUT");
        MvcResult result = mvc.perform(put(ApiKeyController.API_API_KEY_GENERATE)
                .header(AuthorizedControllerHelper.AUTH_HEADER, digestAuthenticateion))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
        ResultData d = objectMapper.readValue(result.getResponse().getContentAsString(), ResultData.class);
    }


    @Test
    public void testDelteKey() throws Exception {
        String uri = ApiKeyController.API_API_KEY_DELETE;
        String digestAuthenticateion = AuthorizedControllerHelper.buildDigestAuthenticateion(mvc, "ykyoon", "1234", uri, "DELETE");
        MvcResult result = mvc.perform(delete(uri)
                .param("apiKeyId", "ABC")
                .header(AuthorizedControllerHelper.AUTH_HEADER, digestAuthenticateion))
                .andExpect(status().isOk())
                .andReturn();
        ResultData d = objectMapper.readValue(result.getResponse().getContentAsString(), ResultData.class);
    }

    @Test
    public void testGetApiKeyList() throws Exception {
        String uri = ApiKeyController.API_API_KEY_LIST;
        String digestAuthenticateion = AuthorizedControllerHelper.buildDigestAuthenticateion(mvc, "ykyoon", "1234", uri, "GET");
        MvcResult result = mvc.perform(get(uri)
                                        .param("pageIndex", "0")
                                        .param("pageSize", "10")
                                        .header(AuthorizedControllerHelper.AUTH_HEADER, digestAuthenticateion))
                            .andDo(MockMvcResultHandlers.print())
                            .andExpect(status().isOk())
                            .andReturn();
        ResultData d = objectMapper.readValue(result.getResponse().getContentAsString(), ResultData.class);
    }

    @Test
    public void testAddProgram() throws Exception {
        String uri = ApiKeyController.API_API_KEY_PROGRAM;
        String digestAuthenticateion = AuthorizedControllerHelper.buildDigestAuthenticateion(mvc, "ykyoon", "1234", uri, "PUT");
        MvcResult result = mvc.perform(put(uri)
                .param("apiKeyId", apiKeyId)
                .header(AuthorizedControllerHelper.AUTH_HEADER, digestAuthenticateion))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
        ResultData d = objectMapper.readValue(result.getResponse().getContentAsString(), ResultData.class);
    }

    @Test
    public void testGetPrograms() throws Exception {
        String uri = ApiKeyController.API_API_KEY_PROGRAMS;
        String digestAuthenticateion = AuthorizedControllerHelper.buildDigestAuthenticateion(mvc, "ykyoon", "1234", uri, "GET");
        MvcResult result = mvc.perform(get(uri)
                .param("apiKeyId", apiKeyId)
                .header(AuthorizedControllerHelper.AUTH_HEADER, digestAuthenticateion))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
        ResultData d = objectMapper.readValue(result.getResponse().getContentAsString(), ResultData.class);
    }

    @Test
    public void testDeleteProgram() throws Exception {
        AcceptProgram program = addProgramForTest();
        Integer programId = program.getId();


    }

    @Transactional
    private AcceptProgram addProgramForTest() throws IllegalAccessException {
        ApiKey apiKey = apiKeyService.getApiKey(apiKeyId);
        return apiKeyService.addProgramTo(USER_ID, apiKey, "ykyoon", "description");
    }
}
