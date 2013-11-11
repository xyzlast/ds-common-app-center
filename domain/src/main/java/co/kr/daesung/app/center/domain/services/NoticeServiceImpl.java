package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.board.Notice;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/11/13
 * Time: 11:10 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {
    

    @Override
    public int getNoticeCount() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Page<Notice> getNotices(int pageIndex, int pageSize, boolean includeDelete) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Notice getNotice(int id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Notice write(String userId, String title, String content, boolean isTop) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Notice edit(String userId, int id, String title, String content, boolean isTop) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean deleteNotice(int id, boolean force) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
