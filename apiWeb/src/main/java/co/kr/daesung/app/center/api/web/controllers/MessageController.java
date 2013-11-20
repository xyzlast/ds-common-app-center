package co.kr.daesung.app.center.api.web.controllers;

import co.kr.daesung.app.center.api.web.aops.AllowCrossDomain;
import co.kr.daesung.app.center.api.web.aops.ApiKeyRequired;
import co.kr.daesung.app.center.api.web.aops.Jsonp;
import co.kr.daesung.app.center.api.web.aops.ResultDataFormat;
import co.kr.daesung.app.center.domain.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 1:14 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class MessageController {

    public static final String API_MESSAGE_CREW = "/api/message/crew";
    public static final String API_MESSAGE_SEND_CREW_MESSAGE_OLD = "/Api/Message/SendCrewMessage";
    public static final String API_MESSAGE_SEND_NLEADER_MESSAGE_OLD = "/Api/Message/SendNLeaderMessage";
    public static final String API_MESSAGE_NLEADER = "/api/message/nleader";
    public static final String API_MESSAGE_CREW_LIST = "/api/message/crew/list";
    public static final String API_MESSAGE_NLEADER_LIST = "/api/message/nleader/list";
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = {API_MESSAGE_CREW, API_MESSAGE_SEND_CREW_MESSAGE_OLD }, method = { RequestMethod.GET, RequestMethod.POST })
    @ResultDataFormat
    @Jsonp
    @ResponseBody
    @ApiKeyRequired
    @AllowCrossDomain
    public Object addMessageForCrewMessage(HttpServletRequest request, HttpServletResponse response,
                                           String key,
                                           String fromUser, String toUser,
                                           String message, String linkUrl, boolean force) {
        return messageService.addCrewMessageToQueue(key, fromUser, toUser, message, linkUrl, force);
    }

    @RequestMapping(value = {API_MESSAGE_NLEADER, API_MESSAGE_SEND_NLEADER_MESSAGE_OLD}, method = { RequestMethod.GET, RequestMethod.POST })
    @ResultDataFormat
    @Jsonp
    @ResponseBody
    @ApiKeyRequired
    @AllowCrossDomain
    public Object addMessageForNLeader(HttpServletRequest request, HttpServletResponse response,
                                       String key,
                                       String fromUser, String toUser, String title, String content, boolean force) {
        return messageService.addNLeaderMessageToQueue(key, fromUser, toUser, title, content, force);
    }

    @RequestMapping(value = API_MESSAGE_CREW_LIST)
    @ResultDataFormat
    @Jsonp
    @ResponseBody
    @ApiKeyRequired
    @AllowCrossDomain
    public Object getCrewMessagesStatus(HttpServletRequest request, HttpServletResponse response,
                                    String key, int pageIndex, int pageSize) {
        return messageService.getCrewMessages(pageIndex, pageSize).getContent();
    }

    @RequestMapping(value = API_MESSAGE_NLEADER_LIST)
    @ResultDataFormat
    @Jsonp
    @ResponseBody
    @ApiKeyRequired
    @AllowCrossDomain
    public Object getNLeaderMessagesStatus(HttpServletRequest request, HttpServletResponse response,
                                           String key, int pageIndex, int pageSize) {
        return messageService.getNLeaderMessages(pageIndex, pageSize).getContent();
    }

}
