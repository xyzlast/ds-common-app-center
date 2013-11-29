package co.kr.daesung.app.center.api.web.controllers;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/29/13
 * Time: 5:44 PM
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
import co.kr.daesung.app.center.domain.entities.board.Notice;
import co.kr.daesung.app.center.domain.services.NoticeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DomainConfiguration.class, SecurityConfiguration.class, ControllerConfiguration.class })
@WebAppConfiguration
public class AdminNoticeControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private NoticeService noticeService;

    @Before
    public void setUp() throws Exception {
        assertThat(context, is(not(nullValue())));
        mvc = AuthorizedControllerHelper.getSecurityAppliedMockMvc(context);
    }

    @Test
    public void accessDenyCommonUser() throws Exception {
        MockHttpSession session = AuthorizedControllerHelper.buildSecuritySession(context, "common");
        mvc.perform(get(AdminNoticeController.API_ADMIN_NOTICES_LIST)
                .param("pageIndex", "0")
                .param("pageSize", "10").session(session))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllNotices() throws Exception {
        MockHttpSession session = AuthorizedControllerHelper.buildSecuritySession(context, "ykyoon");
        MvcResult result = mvc.perform(get(AdminNoticeController.API_ADMIN_NOTICES_LIST)
                .param("pageIndex", "0")
                .param("pageSize", "10").session(session))
                .andExpect(status().isOk())
                .andReturn();
        checkResultData(result);
    }

    @Test
    public void hideNotice() throws Exception {
        final Page<Notice> notices = noticeService.getNotices(0, 10, false);
        Notice notice = notices.getContent().get(0);
        MockHttpSession session = AuthorizedControllerHelper.buildSecuritySession(context, "ykyoon");
        MvcResult result = mvc.perform(post(AdminNoticeController.API_ADMIN_NOTICE_HIDE)
                    .param("noticeId", notice.getId().toString())
                    .session(session))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        checkResultData(result);
    }

    @Test
    public void editNotice() throws Exception {
        final Page<Notice> notices = noticeService.getNotices(0, 10, false);
        Notice notice = notices.getContent().get(0);
        MockHttpSession session = AuthorizedControllerHelper.buildSecuritySession(context, "ykyoon");
        MvcResult result = mvc.perform(post(AdminNoticeController.API_ADMIN_NOTICE_EDIT)
                .param("noticeId", notice.getId().toString())
                .param("title", "EDITED_" + notice.getTitle())
                .param("content", "EDITED_" + notice.getContent())
                .param("top", Boolean.TRUE.toString())
                .session(session))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        checkResultData(result);
    }

    @Test
    public void writeNotice() throws Exception {
        MockHttpSession session = AuthorizedControllerHelper.buildSecuritySession(context, "ykyoon");
        MvcResult result = mvc.perform(post(AdminNoticeController.API_ADMIN_NOTICE_WRITE)
                .param("title", "NEW_NOTICE")
                .param("content", "NEW_CONTENT")
                .param("top", Boolean.TRUE.toString())
                .session(session))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        checkResultData(result);
    }

    @Test
    public void showNotice() throws Exception {
        final Page<Notice> notices = noticeService.getNotices(0, 10, true);
        Notice notice = notices.getContent().get(0);
        MockHttpSession session = AuthorizedControllerHelper.buildSecuritySession(context, "ykyoon");
        MvcResult result = mvc.perform(post(AdminNoticeController.API_ADMIN_NOTICE_SHOW)
                .param("noticeId", notice.getId().toString())
                .session(session))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        checkResultData(result);
    }

    private void checkResultData(MvcResult result) throws Exception {
        ResultData r = objectMapper.readValue(result.getResponse().getContentAsString(), ResultData.class);
        assertThat(r.isOk(), is(true));
    }
}
