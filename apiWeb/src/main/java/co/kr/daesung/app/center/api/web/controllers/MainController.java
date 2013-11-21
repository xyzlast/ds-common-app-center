package co.kr.daesung.app.center.api.web.controllers;

import co.kr.daesung.app.center.api.web.aops.AllowCrossDomain;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 1:23 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class MainController {
    @RequestMapping(value = { "/", "/index.html" })
    public String redirectToMain() {
        return "redirect:http://www.daum.net";
    }
}
