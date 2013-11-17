package co.kr.daesung.app.center.api.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
