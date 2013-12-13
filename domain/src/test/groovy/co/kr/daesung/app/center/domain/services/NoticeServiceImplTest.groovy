package co.kr.daesung.app.center.domain.services

import co.kr.daesung.app.center.domain.configs.DomainConfiguration
import co.kr.daesung.app.center.domain.entities.board.Notice
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by ykyoon on 12/11/13.
 */
@ContextConfiguration(classes = [ DomainConfiguration ])
class NoticeServiceImplTest extends Specification {
    public static final String USER_ID = "ykyoon"

    @Autowired
    NoticeService service;

    def write() {
        def title = "TITLE"
        def content = "CONTENT"

        Notice notice = service.write(USER_ID, title, content, false)
        notice != null
        notice.writer == USER_ID
        return notice
    }

    def '공지사항 ID로 얻어내기'() {
        when:
        def notices = service.getNotices(0, 10, true)
        then:
        notices.every {
            def id = it.id;
            def notice = service.getNotice(id)
            notice != null
        }
    }

    def '공지사항 갯수 확인'() {
        expect:
        def count = service.getNoticeCount(includeDelete)
        count >= limit
        where:
        includeDelete | limit
        true | 0
        false | 0
    }

    def '공지사항 작성'() {
        when:
        def notice = write()
        then:
        notice != null
        def writedNotice = service.getNotice(notice.id)
        writedNotice.title == notice.title
        writedNotice.content == notice.content
        writedNotice.writer == notice.writer
    }

    def '공지사항 삭제'() {
        when:
        def notice = write()
        service.hideNotice(notice.id)
        then:
        def updatedNotice = service.getNotice(notice.id)
        updatedNotice.deleted == true
    }

    def '삭제된 공지사항 복원'() {
        when:
        def notice = write()
        service.hideNotice(notice.id)
        then:
        def updatedNotice = service.getNotice(notice.id)
        updatedNotice.deleted == true

        when:
        service.showNotice(notice.id)
        then:
        def shownNotice = service.getNotice(notice.id)
        shownNotice.deleted == false
    }

    def '공지사항 수정'() {
        when:
        def notice = write()
        service.edit(notice.id, "EDITED_" + notice.title, "EDITED_" + notice.content, true)
        def editedNotice = service.getNotice(notice.id)
        then:
        editedNotice.title.startsWith("EDITED_") == true
        editedNotice.content.startsWith("EDITED") == true
        editedNotice.top == true
    }

    def '공지사항 수정시, 에러 발생'() {
        when:
        def notice = write()
        service.edit(USER_ID, notice.id, "", "", false)
        then:
        thrown(IllegalArgumentException)
    }
}
