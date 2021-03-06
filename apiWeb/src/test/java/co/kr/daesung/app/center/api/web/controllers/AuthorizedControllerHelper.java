package co.kr.daesung.app.center.api.web.controllers;

import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

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

/**
 * User: ykyoon
 * Date: 11/18/13
 * Time: 7:13 PM
 * Spring Security Filter 적용 및 인증 지원을 위한 Test Helper Class
 */
public class AuthorizedControllerHelper {

    /**
     * MockMvc 생성 코드
     * @param context WebApplicationContext
     * @return Spring Security Filter가 적용된 MockMvc 객체
     * @throws Exception
     */
    public static MockMvc getSecurityAppliedMockMvc(WebApplicationContext context) throws Exception {
        DelegatingFilterProxy delegateProxyFilter = new DelegatingFilterProxy();
        MockFilterConfig secFilterConfig = new MockFilterConfig(context.getServletContext(),
                BeanIds.SPRING_SECURITY_FILTER_CHAIN);
        delegateProxyFilter.init(secFilterConfig);

        return MockMvcBuilders.webAppContextSetup(context).addFilter(delegateProxyFilter).build();
    }

    public static final String AUTH_HEADER = "Authorization";

    /**
     * Basic 인증 문자열 생성
     * @param username 사용자 이름
     * @param password 비밀번호
     * @return Basic XXXX 형태의 Authorization 문자열
     * @throws Exception
     */
    public static final String buildBasicAuthHeaderValue(String username, String password) throws Exception {
        String authHeaderFormat = "Basic ";
        String encodingRawData = String.format("%s:%s", username, password);
        String encodingData = authHeaderFormat + new String(Base64.encode(encodingRawData.getBytes("utf-8")));
        return encodingData;
    }

    /**
     * Digest 인증 문자열 생성
     * @param mvc MockMvc. Digest의 경우, 한번의 Request를 통해 서버의 nonce값을 얻어내야지 된다.
     *            Spring Security의 EntryEndPoint의 설정이 DigestAuthenticationFilter로 되어있어야지 된다.
     * @param username 사용자 이름
     * @param password 비밀번호
     * @param uri 호출할 URI
     * @param method HttpRequestMethod : GET, POST, PUT, DELETE
     * @return Digest 인증 문자열
     * @throws Exception
     */
    public static String buildDigestAuthenticateion(MockMvc mvc, String username,
                                                    String password,
                                                    String uri, String method) throws Exception {
        MvcResult mvcResult = null;
        if(method.equals("GET")) {
            mvcResult = mvc.perform(get(uri)).andDo(print()).andReturn();
        } else if(method.equals("POST")) {
            mvcResult = mvc.perform(post(uri)).andDo(print()).andReturn();
        } else if(method.equals("PUT")) {
            mvcResult = mvc.perform(put(uri)).andDo(print()).andReturn();
        } else if(method.equals("DELETE")) {
            mvcResult = mvc.perform(delete(uri)).andDo(print()).andReturn();
        }
        String authHeader = mvcResult.getResponse().getHeader("WWW-Authenticate");
        String[] authHeaderItemStrings = authHeader.split(",\\s");
        Map<String, String> authItems = new HashMap<>();
        Pattern keyAndItemPattern = Pattern.compile("(Digest\\s)?(?<key>[^=]+)=\"(?<value>[^\"]+)\"");
        for(int i = 0 ; i < authHeaderItemStrings.length; i++) {
            Matcher matcher = keyAndItemPattern.matcher(authHeaderItemStrings[i]);
            assertThat(matcher.find(), is(true));
            String key = matcher.group("key");
            String value = matcher.group("value");
            authItems.put(key, value);
        }
        Assert.assertNotNull(authItems.get("realm"));
        Assert.assertNotNull(authItems.get("nonce"));
        Assert.assertNotNull(authItems.get("qop"));

        String ha1 = DigestUtils.md5DigestAsHex(String.format("%s:%s:%s", username, authItems.get("realm"), password).getBytes("UTF-8"));
        String ha2 = DigestUtils.md5DigestAsHex(String.format("%s:%s", method, uri).getBytes("UTF-8"));
        String cnonce = calculateNonce();
        String totalString = String.format("%s:%s:00000001:%s:%s:%s",
                ha1, authItems.get("nonce"), cnonce, authItems.get("qop"), ha2);
        String response = DigestUtils.md5DigestAsHex(totalString.getBytes("UTF-8"));

        String clientRequest = String.format("Digest username=\"%s\",", username) +
                String.format("realm=\"%s\",", authItems.get("realm")) +
                String.format("nonce=\"%s\",", authItems.get("nonce")) +
                String.format("uri=\"%s\",", uri) +
                String.format("qop=%s,", authItems.get("qop")) +
                "nc=00000001," +
                String.format("cnonce=\"%s\",", cnonce) +
                String.format("response=\"%s\"", response);

        return clientRequest;
    }

    /**
     * Form인증을 위한 MockHttpSession 반환 함수
     * @param context WebApplicationContext
     * @param username 사용자 이름
     * @return Spring Security Attribute가 적용된 MockHttpSession 값
     * @throws Exception
     */
    public static MockHttpSession buildSecuritySession(WebApplicationContext context, String username) throws Exception {
        MockHttpSession session = new MockHttpSession();
        SecurityContext securityContext = buildFormAuthentication(context, username);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
        return session;
    }

    /**
     * Spring Security Context 얻어내는 내부 함수
     * @param context WebApplicationContext
     * @param username 사용자 이름
     * @return SecurityContext
     * @throws Exception
     */
    private static SecurityContext buildFormAuthentication(WebApplicationContext context, String username) throws Exception {
        UserDetailsService userDetailsService = (UserDetailsService) context
                .getBean(BeanIds.USER_DETAILS_SERVICE);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());
        SecurityContext securityContext = new SecurityContext() {
            private static final long serialVersionUID = 8611087650974958658L;
            private Authentication authentication;

            @Override
            public void setAuthentication(Authentication authentication) {
                this.authentication = authentication;
            }

            @Override
            public Authentication getAuthentication() {
                return this.authentication;
            }
        };
        securityContext.setAuthentication(authToken);
        return securityContext;
    }

    /**
     * Digest인증시에 사용되는 cnonce 값을 생성하는 내부 함수
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String calculateNonce() throws UnsupportedEncodingException {
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy:MM:dd:hh:mm:ss");
        String fmtDate = f.format(d);
        Random rand = new Random(100000);
        Integer randomInt = rand.nextInt();
        return DigestUtils.md5DigestAsHex((fmtDate + randomInt.toString()).getBytes("UTF-8"));
    }
}
