package co.kr.daesung.app.center.api.web.controllers;

import co.kr.daesung.app.center.api.web.aops.ResultDataFormat;
import co.kr.daesung.app.center.api.web.auth.UserInfoHelper;
import co.kr.daesung.app.center.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoHelper userInfoHelper;

    @RequestMapping("/auth/getUserInfo")
    @ResultDataFormat
    public Object getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        return userInfoHelper.getUserFromRequest(request);
    }
}
