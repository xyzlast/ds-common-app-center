package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.board.Notice;
import org.springframework.data.domain.Page;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/11/13
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
public interface NoticeService {
    long getNoticeCount(boolean includeDeleted);
    Page<Notice> getNotices(int pageIndex, int pageSize, boolean includeDelete);
    Notice getNotice(int id);
    Notice write(String userId, String title, String content, boolean isTop);
    Notice showNotice(int id);
    Notice hideNotice(int id);
    Notice edit(String userId, int id, String title, String content, boolean isTop);
    boolean deleteNotice(int id, boolean force);
}
