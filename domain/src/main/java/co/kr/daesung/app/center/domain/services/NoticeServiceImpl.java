package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.board.Notice;
import co.kr.daesung.app.center.domain.entities.board.QNotice;
import co.kr.daesung.app.center.domain.repo.board.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    @Transactional(readOnly = true)
    public long getNoticeCount(boolean includeDeleted) {
        if(!includeDeleted) {
            QNotice qNotice = QNotice.notice;
            return noticeRepository.count(qNotice.deleted.isFalse());
        } else {
            return noticeRepository.count();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Notice> getNotices(int pageIndex, int pageSize, boolean includeDelete) {
        PageRequest pageRequest = new PageRequest(pageIndex, pageSize);
        if(includeDelete) {
            QNotice qNotice = QNotice.notice;
            return noticeRepository.findAll(qNotice.deleted.isFalse(), pageRequest);
        } else {
            return noticeRepository.findAll(pageRequest);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Notice getNotice(int id) {
        return noticeRepository.findOne(id);
    }

    @Override
    public Notice write(String userId, String title, String content, boolean isTop) {
        Notice notice = new Notice();
        notice.setWriter(userId);
        notice.setTitle(title);;
        notice.setContent(content);
        notice.setTop(isTop);
        noticeRepository.save(notice);
        return notice;
    }

    @Override
    public Notice edit(String userId, int id, String title, String content, boolean isTop) {
        Notice notice = getNotice(id);
        notice.setWriter(userId);
        notice.setTitle(title);
        notice.setContent(content);
        notice.setTop(isTop);
        noticeRepository.save(notice);

        if(title.equals("")) {
            throw new IllegalArgumentException("");
        }

        return notice;
    }

    @Override
    public boolean deleteNotice(int id, boolean force) {
        Notice notice = getNotice(id);
        if(force) {
            noticeRepository.delete(notice);
        } else {
            notice.setDeleted(true);
            noticeRepository.save(notice);
        }
        return true;
    }
}
