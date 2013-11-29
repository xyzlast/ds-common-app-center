package co.kr.daesung.app.center.api.web.controllers;

import co.kr.daesung.app.center.api.web.aops.ResultDataFormat;
import co.kr.daesung.app.center.domain.services.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/29/13
 * Time: 5:38 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class AdminNoticeController {
    public static final String API_ADMIN_NOTICES_LIST = "/api/admin/notice/list";
    public static final String API_ADMIN_NOTICE_SHOW = "/api/admin/notice/show";
    public static final String API_ADMIN_NOTICE_HIDE = "/api/admin/notice/hide";
    public static final String API_ADMIN_NOTICE_EDIT = "/api/admin/notice/edit";
    public static final String API_ADMIN_NOTICE_WRITE = "/api/admin/notice/write";
    @Autowired
    private NoticeService noticeService;

    @RequestMapping(value = API_ADMIN_NOTICES_LIST)
    @ResponseBody
    @ResultDataFormat
    public Object getAllNotices(int pageIndex, int pageSize) {
        return noticeService.getNotices(pageIndex, pageSize, true);
    }

    @RequestMapping(value = API_ADMIN_NOTICE_SHOW, method = RequestMethod.POST)

    @ResponseBody
    @ResultDataFormat
    public Object showNotice(int noticeId) {
        return noticeService.showNotice(noticeId);
    }

    @RequestMapping(value = API_ADMIN_NOTICE_HIDE, method = RequestMethod.POST)
    @ResponseBody
    @ResultDataFormat
    public Object hideNotice(int noticeId) {
        return noticeService.hideNotice(noticeId);
    }

    @RequestMapping(value = API_ADMIN_NOTICE_EDIT, method = RequestMethod.POST)
    @ResponseBody
    @ResultDataFormat
    public Object editNotice(HttpServletRequest request, int noticeId, String title, String content, boolean top) {
        String username = request.getUserPrincipal().getName();
        return noticeService.edit(username, noticeId, title, content, top);
    }

    @RequestMapping(value = API_ADMIN_NOTICE_WRITE, method = RequestMethod.POST)
    @ResponseBody
    @ResultDataFormat
    public Object writeNotice(HttpServletRequest request, String title, String content, boolean top) {
        String username = request.getUserPrincipal().getName();
        return noticeService.write(username, title, content, top);
    }
}
