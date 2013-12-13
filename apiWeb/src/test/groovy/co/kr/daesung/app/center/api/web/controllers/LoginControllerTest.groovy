package co.kr.daesung.app.center.api.web.controllers

import co.kr.daesung.app.center.api.web.configs.SecurityConfiguration
import co.kr.daesung.app.center.api.web.configs.ControllerConfiguration
import co.kr.daesung.app.center.api.web.vos.ResultData
import co.kr.daesung.app.center.domain.configs.DomainConfiguration
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

/**
 * Created by ykyoon on 12/12/13.
 */
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*

@WebAppConfiguration
@ContextConfiguration(classes = [DomainConfiguration, SecurityConfiguration, ControllerConfiguration])
class LoginControllerTest extends Specification {
    @Autowired
    WebApplicationContext context
    @Autowired
    ObjectMapper objectMapper
    MockMvc mvc

    def setup() {
        mvc = AuthorizedControllerHelper.getSecurityAppliedMockMvc(context)
    }

    def '인증되지 않은 사용자가 로그인정보를 얻어올때'() {
        when:
        def mvcResult = mvc.perform(get(LoginController.API_LOGIN))
                           .andReturn()
        then:
        mvcResult.response.status == HttpStatus.OK.value()
        def content = mvcResult.getResponse().getContentAsString()
        ResultData resultData = objectMapper.readValue(content, ResultData)
        resultData.ok == true
        Map<String, Object> data = resultData.data;
        data.get("authenticated") == false
    }

    def '로그인 하는 과정'() {
        expect:
        def mvcResult = mvc.perform(post(LoginController.API_LOGIN)
                               .param("username", username)
                               .param("password", password))
                           .andReturn()
        mvcResult.response.status == httpResult
        where:
        username | password | httpResult
        'ykyoon' | '1234'   | HttpStatus.OK.value()
        'ykyoon' | 'wrongP' | HttpStatus.UNAUTHORIZED.value()
    }
}
