package co.kr.daesung.app.center.api.web.controllers;

import co.kr.daesung.app.center.api.web.aops.AllowCrossDomain;
import co.kr.daesung.app.center.api.web.aops.ApiKeyRequired;
import co.kr.daesung.app.center.api.web.aops.Jsonp;
import co.kr.daesung.app.center.api.web.aops.ResultDataFormat;
import co.kr.daesung.app.center.domain.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/20/13
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class PublicController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/api/public/crew/list")
    @ResultDataFormat
    @Jsonp
    @ResponseBody
    @AllowCrossDomain
    public Object getCrewMessagesStatus(HttpServletRequest request, HttpServletResponse response,
                                        String key, int pageIndex, int pageSize) {
        return messageService.getCrewMessages(pageIndex, pageSize).getContent();
    }

    @RequestMapping(value = "/api/public/nleader/list")
    @ResultDataFormat
    @Jsonp
    @ResponseBody
    @AllowCrossDomain
    public Object getNLeaderMessagesStatus(HttpServletRequest request, HttpServletResponse response,
                                           String key, int pageIndex, int pageSize) {
        return messageService.getNLeaderMessages(pageIndex, pageSize).getContent();
    }
}
