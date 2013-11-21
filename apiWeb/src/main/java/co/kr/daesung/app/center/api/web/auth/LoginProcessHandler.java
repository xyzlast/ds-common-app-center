package co.kr.daesung.app.center.api.web.auth;

import co.kr.daesung.app.center.api.web.vos.ResultData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/21/13
 * Time: 10:46 AM
 * To change this template use File | Settings | File Templates.
 */

public class LoginProcessHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    @Getter
    @Setter
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ResultData resultData = new ResultData("Login success");
        final PrintWriter writer = response.getWriter();
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        response.addHeader("Access-Control-Allow-Methods", "'DELETE', 'PUT', 'GET', 'POST', 'OPTION'");
//        response.addHeader("Access-Control-Max-Age", "1000");
        writer.write(objectMapper.writeValueAsString(resultData));
        writer.close();
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResultData resultData = new ResultData(exception);
        final PrintWriter writer = response.getWriter();
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        response.addHeader("Access-Control-Allow-Methods", "'DELETE', 'PUT', 'GET', 'POST', 'OPTION'");
//        response.addHeader("Access-Control-Max-Age", "1000");
        writer.write(objectMapper.writeValueAsString(resultData));
        writer.close();
    }
}
