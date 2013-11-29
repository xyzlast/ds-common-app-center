package co.kr.daesung.app.center.api.web.controllers;

import co.kr.daesung.app.center.api.web.aops.ResultDataFormat;
import co.kr.daesung.app.center.domain.entities.auth.User;
import co.kr.daesung.app.center.domain.services.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/21/13
 * Time: 1:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class LoginController {
    public static final String API_LOGIN = "/api/login";
    public static final String API_LOGOUT = "/api/logout";
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @RequestMapping(value= API_LOGIN, method = {RequestMethod.GET, RequestMethod.OPTIONS})
    @ResponseBody
    @ResultDataFormat
    public Object getAuthenticationStatus(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);
        if ( session != null &&
                auth != null && !auth.getName().equals("anonymousUser") && auth.isAuthenticated()
            ) {
            User user = userService.findByUsername(auth.getName());
            if(user != null) {
                return new LoginStatus(true, auth.getName(), user.getName(), user.containAdminRole());
            } else {
                return new LoginStatus(false, null, null, false);
            }
        } else {
            return new LoginStatus(false, null, null, false);
        }
    }

    @RequestMapping(value= API_LOGIN, method = RequestMethod.POST)
    @ResponseBody
    @ResultDataFormat
    public Object login(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            User user = userService.findByUsername(username);
            token.setDetails(user);
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            return new LoginStatus(auth.isAuthenticated(), user.getUsername(), user.getName(), user.containAdminRole());
        } catch (Exception e) {
            return new LoginStatus(false, null, null, false);
        }
    }

    @RequestMapping(value = API_LOGOUT)
    @ResponseBody
    @ResultDataFormat
    public Object logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return new LoginStatus(false, null, null, false);
    }

    @Getter
    public class LoginStatus {
        private final boolean authenticated;
        private final String username;
        private final String name;
        private final boolean admin;
        public LoginStatus(boolean loggedIn, String username, String name, boolean admin) {
            this.authenticated = loggedIn;
            this.username = username;
            this.name = name;
            this.admin = admin;
        }
    }
}
