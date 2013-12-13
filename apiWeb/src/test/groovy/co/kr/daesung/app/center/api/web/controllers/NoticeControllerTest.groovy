package co.kr.daesung.app.center.api.web.controllers

import co.kr.daesung.app.center.api.web.configs.ControllerConfiguration
import co.kr.daesung.app.center.api.web.configs.SecurityConfiguration
import co.kr.daesung.app.center.api.web.vos.ResultData
import co.kr.daesung.app.center.domain.configs.DomainConfiguration
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*

/**
 * Created by ykyoon on 12/12/13.
 */
@ContextConfiguration(classes = [DomainConfiguration, SecurityConfiguration, ControllerConfiguration])
@WebAppConfiguration
class NoticeControllerTest extends Specification {
    @Autowired
    private WebApplicationContext context
    @Autowired
    private ObjectMapper objectMapper
    MockMvc mvc;

    def setup() {
        mvc = AuthorizedControllerHelper.getSecurityAppliedMockMvc(context)
    }

    def '공지사항 list json output 확인'() {
        when:
        def mvcResult = mvc.perform(get(NoticeController.API_NOTICE_LIST)
                           .param("pageIndex", "0")
                           .param("pageSize", "10"))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn();
        then:
        def responseString = mvcResult.response.getContentAsString()
        ResultData resultData = objectMapper.readValue(responseString, ResultData)
        resultData.ok == true
    }
}
