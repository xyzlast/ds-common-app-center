package co.kr.daesung.app.center.api.web.controllers;

import co.kr.daesung.app.center.api.web.aops.ResultDataFormat;
import co.kr.daesung.app.center.domain.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 12/2/13
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class AdminCrewMessageController {
    public static final String API_ADMIN_CREW_LIST = "/api/admin/crew/list";
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = API_ADMIN_CREW_LIST, method = RequestMethod.GET)
    @ResponseBody
    @ResultDataFormat
    public Object getCrewMessages(int pageIndex, int pageSize) {
        return messageService.getCrewMessages(pageIndex, pageSize);
    }
}
