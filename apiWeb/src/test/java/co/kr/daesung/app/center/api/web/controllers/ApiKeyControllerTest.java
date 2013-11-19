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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
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
                .header(AuthorizedControllerHelper.AUTH_HEADER, AuthorizedControllerHelper.getAuthHeaderValue("ykyoon", "1234")))
                .andExpect(status().isOk());
    }

    private static String getDigestAuthenticateion(MockMvc mvc, String username,
                                                   String password, String realm,
                                                   String uri, String method) throws Exception {
        MvcResult mvcResult = null;
        if(method.equals("GET")) {
            mvcResult = mvc.perform(get(uri)).andDo(print()).andReturn();
        } else if(method.equals("POST")) {
            mvcResult = mvc.perform(post(uri)).andReturn();
        } else if(method.equals("PUT")) {
            mvcResult = mvc.perform(put(uri)).andReturn();
        } else if(method.equals("DELETE")) {
            mvcResult = mvc.perform(delete(uri)).andReturn();
        }
        String authHeader = mvcResult.getResponse().getHeader("WWW-Authenticate");
        String[] authHeaderItemStrings = authHeader.split(",\\s");
        Map<String, String> authItems = new HashMap<>();
        Pattern keyAndItemPattern = Pattern.compile("(Digest\\s)?(?<key>[^=]+)=\"(?<value>[^\"]+)\"");
        for(int i = 0 ; i < authHeaderItemStrings.length; i++) {
            System.out.println(authHeaderItemStrings[i]);
            Matcher matcher = keyAndItemPattern.matcher(authHeaderItemStrings[i]);
            assertThat(matcher.find(), is(true));
            String key = matcher.group("key");
            String value = matcher.group("value");
            authItems.put(key, value);
        }
        Assert.assertNotNull(authItems.get("realm"));
        Assert.assertNotNull(authItems.get("nonce"));
        Assert.assertNotNull(authItems.get("qop"));

        String ha1 = DigestUtils.md5DigestAsHex(String.format("%s:%s:%s", username, realm, password).getBytes("UTF-8"));
        String ha2 = DigestUtils.md5DigestAsHex(String.format("%s:%s", method, uri).getBytes("UTF-8"));
        String cnonce = calculateNonce();
        String totalString = String.format("%s:%s:00000001:%s:%s:%s",
                ha1, authItems.get("nonce"), cnonce, authItems.get("qos"), ha2);
        String response = DigestUtils.md5DigestAsHex(totalString.getBytes("UTF-8"));

        String clientRequest = String.format("username=\"%s\",", username) +
                String.format("realm=\"%s\",", realm) +
                String.format("nonce=\"%s\",", authItems.get("nonce")) +
                String.format("uri=\"%s\",", uri) +
                String.format("qop=%s,", authItems.get("qop")) +
                "nc=00000001," +
                String.format("cnonce=\"%s\",", cnonce) +
                String.format("response=\"%s\"", response);// + ",opaque=\"\"";

        return clientRequest;
    }

    private static String calculateNonce() throws UnsupportedEncodingException {
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy:MM:dd:hh:mm:ss");
        String fmtDate = f.format(d);
        Random rand = new Random(100000);
        Integer randomInt = rand.nextInt();
        return DigestUtils.md5DigestAsHex((fmtDate + randomInt.toString()).getBytes("UTF-8"));
    }


    @Test
    public void testGenerateKey() throws Exception {
        MvcResult firstRequest = mvc.perform(get("/api/apiKey/generate"))
                .andDo(print())
                .andReturn();
        String authHeader = firstRequest.getResponse().getHeader("WWW-Authenticate");
//        String[] items = authHeader.split(",\\s");
//        String[] i2 = items[2].split("\"");
//        System.out.println(i2[0]);
//        System.out.println(i2[1]);

        String[] authHeaderItemStrings = authHeader.split(",\\s");
        Map<String, String> authItems = new HashMap<>();
        Pattern keyAndItemPattern = Pattern.compile("(Digest\\s)?(?<key>[^=]+)=\"(?<value>[^\"]+)\"");
        for(int i = 0 ; i < authHeaderItemStrings.length; i++) {
            System.out.println(authHeaderItemStrings[i]);
            Matcher matcher = keyAndItemPattern.matcher(authHeaderItemStrings[i]);
            assertThat(matcher.find(), is(true));
            String key = matcher.group("key");
            String value = matcher.group("value");
            authItems.put(key, value);
        }


        String username = "ykyoon";
        String password = "1234";
        String url = "/api/apiKey/generate";
        String realm = "ykyoon";
//        String nonce = i2[1];
        String nonce = authItems.get("nonce");

        String ha1 = DigestUtils.md5DigestAsHex(String.format("%s:%s:%s", username, realm, password).getBytes("UTF-8"));
        String qop = "auth";

        String method = "GET";
        String uri = "/api/apiKey/generate";
        String ha2 = DigestUtils.md5DigestAsHex(String.format("%s:%s", method, uri).getBytes("UTF-8"));

        String clientNonce = calculateNonce();
        String totalString = String.format("%s:%s:00000001:%s:%s:%s",
                ha1, nonce, clientNonce, qop, ha2);
        String response = DigestUtils.md5DigestAsHex(totalString.getBytes("UTF-8"));

        String clientRequest = String.format("username=\"%s\",", username) +
                String.format("realm=\"%s\",", realm) +
                String.format("nonce=\"%s\",", nonce) +
                String.format("uri=\"%s\",", url) +
                "qop=auth," +
                "nc=00000001," +
                String.format("cnonce=\"%s\",", clientNonce) +
                String.format("response=\"%s\"", response);// + ",opaque=\"\"";

//        String clientRequest = getDigestAuthenticateion(mvc, "ykyoon", "1234", "ykyoon", "/api/apiKey/generate", "GET");
        MvcResult result = mvc.perform(get("/api/apiKey/generate")
                .header(AuthorizedControllerHelper.AUTH_HEADER, "Digest " + clientRequest))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
        String contentString = result.getResponse().getContentAsString();
        System.out.println(contentString);

        mvc.perform(get("/api/apiKey/generate")
                .header(AuthorizedControllerHelper.AUTH_HEADER, "Digest " + clientRequest))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        mvc.perform(get("/api/apiKey/generate")
                .header(AuthorizedControllerHelper.AUTH_HEADER, "Digest " + clientRequest))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        mvc.perform(get("/api/apiKey/generate")
                .header(AuthorizedControllerHelper.AUTH_HEADER, "Digest " + clientRequest))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }


    @Test
    public void testDelteKey() throws Exception {
        String authValue = AuthorizedControllerHelper.getAuthHeaderValue("ykyoon", "662a5aae230dd1be88bf1b8dbdb38dc0");
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
        String authValue = AuthorizedControllerHelper.getAuthHeaderValue("ykyoon", "1234");
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
