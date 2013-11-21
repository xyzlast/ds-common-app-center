package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.entities.board.Notice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/11/13
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DomainConfiguration.class })
public class NoticeServiceImplTest {
    @Autowired
    private NoticeService noticeService;

    @Test
    public void testGetNoticeCount() throws Exception {
        long countNotIncludeDeleted = noticeService.getNoticeCount(false);
        assertThat(countNotIncludeDeleted, is(not(0L)));

        long countIncludeDeleted = noticeService.getNoticeCount(true);
        assertThat(countIncludeDeleted, is(not(0L)));
    }

    @Test
    public void testGetNotices() throws Exception {
        Page<Notice> notices = noticeService.getNotices(0, Integer.MAX_VALUE, true);
        assertThat(notices, is(not(nullValue())));
        assertThat(notices.getSize(), is(not(0)));

        Page<Notice> notices2 = noticeService.getNotices(0, Integer.MAX_VALUE, false);
        assertThat(notices2, is(not(nullValue())));
        assertThat(notices2.getSize() <= notices.getSize(), is(true));
    }

    @Test
    public void testGetNotice() throws Exception {
        Page<Notice> notices = noticeService.getNotices(0, 10, true);
        for(Notice notice : notices.getContent()) {
            int noticeId = notice.getId();
            Notice getNoticeResult = noticeService.getNotice(noticeId);
            assertThat(getNoticeResult, is(not(nullValue())));
        }
    }

    private static final String USER_ID = "201105010";

    private Notice writeNotice() {
        String title = "TITLE";
        String content = "CONTENT";

        Notice notice = noticeService.write(USER_ID, title, content, false);
        assertThat(notice, is(not(nullValue())));
        assertThat(notice.getWriter(), is(USER_ID));
        return notice;
    }

    @Test
    public void testWrite() throws Exception {
        writeNotice();
    }

    @Test
    public void testEdit() throws Exception {
        Notice notice = writeNotice();
        Notice editedNotice = noticeService.edit(USER_ID, notice.getId(), notice.getTitle() + "_EDITED", notice.getContent() + "_EDITED", true);
        assertThat(editedNotice.getTitle().endsWith("_EDITED"), is(true));
        assertThat(editedNotice.getContent().endsWith("_EDITED"), is(true));
        assertThat(editedNotice.isTop(), is(true));

        boolean deleteResult = noticeService.deleteNotice(notice.getId(), true);
        assertThat(deleteResult, is(true));
        Notice deletedNotice = noticeService.getNotice(notice.getId());
        assertThat(deletedNotice, is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEdit2() throws Exception {
        Notice notice = writeNotice();
        noticeService.edit(USER_ID, notice.getId(), "", "", false);
        Notice extractedNotice = noticeService.getNotice(notice.getId());
        assertThat(extractedNotice.getTitle(), is(notice.getTitle()));
    }

    @Test
    public void testDeleteNotice() throws Exception {
        Notice notice = writeNotice();
        boolean deleteResult = noticeService.deleteNotice(notice.getId(), false);
        assertThat(deleteResult, is(true));

        Notice deletedNotice = noticeService.getNotice(notice.getId());
        assertThat(deletedNotice.isDeleted(), is(true));
    }
}
