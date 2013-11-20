package co.kr.daesung.app.center.api.web.controllers;

import co.kr.daesung.app.center.api.web.aops.AllowCrossDomain;
import co.kr.daesung.app.center.api.web.aops.Jsonp;
import co.kr.daesung.app.center.api.web.aops.ResultDataFormat;
import co.kr.daesung.app.center.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 5:53 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class AuthController {
    public static final String API_AUTH_USERINFO = "/api/auth/userinfo";
    @Autowired
    private UserService userService;

    @RequestMapping(API_AUTH_USERINFO)
    @ResultDataFormat
    @ResponseBody
    @Jsonp
    @AllowCrossDomain
    public Object getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        return userService.findByUsername(request.getUserPrincipal().getName());
    }
}
