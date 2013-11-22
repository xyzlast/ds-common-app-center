package co.kr.daesung.app.center.api.web.controllers;

import co.kr.daesung.app.center.api.web.aops.ResultDataFormat;
import co.kr.daesung.app.center.domain.services.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/21/13
 * Time: 2:23 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class NoticeController {
    public static final String API_NOTICE_LIST = "/api/notice/list";
    @Autowired
    private NoticeService noticeService;

    @RequestMapping(value = API_NOTICE_LIST)
    @ResultDataFormat
    @ResponseBody
    public Object getNotices(HttpServletRequest request, HttpServletResponse response, int pageIndex, int pageSize) {
        return noticeService.getNotices(pageIndex, pageSize, false);
    }
}
