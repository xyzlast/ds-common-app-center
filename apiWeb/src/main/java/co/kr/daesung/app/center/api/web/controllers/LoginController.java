package co.kr.daesung.app.center.api.web.controllers;

import co.kr.daesung.app.center.api.web.aops.AllowCrossDomain;
import co.kr.daesung.app.center.api.web.aops.ResultDataFormat;
import co.kr.daesung.app.center.domain.entities.auth.User;
import co.kr.daesung.app.center.domain.services.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/21/13
 * Time: 1:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class LoginController {
    @Autowired
    private org.springframework.security.authentication.AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @RequestMapping(value="/api/login", method = {RequestMethod.GET, RequestMethod.OPTIONS})
    @ResponseBody
    @ResultDataFormat
    public Object getAuthenticationStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser") && auth.isAuthenticated()) {
            return new LoginStatus(true, auth.getName());
        } else {
            return new LoginStatus(false, null);
        }
    }

    @RequestMapping(value="/api/login", method = RequestMethod.POST)
    @ResponseBody
    @ResultDataFormat
    public Object login(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            User details = userService.findByUsername(username);
            token.setDetails(details);
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            return new LoginStatus(auth.isAuthenticated(), auth.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new LoginStatus(false, null);
        }
    }

    @Getter
    public class LoginStatus {
        private final boolean isAuthenticated;
        private final String username;
        public LoginStatus(boolean loggedIn, String username) {
            this.isAuthenticated = loggedIn;
            this.username = username;
        }
    }

}
