package co.kr.daesung.app.center.api.web.configs;

import co.kr.daesung.app.center.api.web.auth.CommonAppUserDetailService;
import co.kr.daesung.app.center.api.web.auth.StringEncrypter;
import co.kr.daesung.app.center.api.web.auth.UserInfoHelper;
import co.kr.daesung.app.center.api.web.auth.UserInfoHelperImpl;
import co.kr.daesung.app.center.domain.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 1:12 AM
 * To change this template use File | Settings | File Templates.
 */

@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private ApplicationContext context;

    @Bean
    public DigestAuthenticationFilter digestAuthenticationFilter() throws Exception {
        DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
        digestAuthenticationFilter.setAuthenticationEntryPoint(digestAuthenticationEntryPoint());
        digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());

        return digestAuthenticationFilter;
    }

    @Bean
    public DigestAuthenticationEntryPoint digestAuthenticationEntryPoint() {
        DigestAuthenticationEntryPoint entryPoint = new DigestAuthenticationEntryPoint();
        entryPoint.setRealmName("ykyoon");
        entryPoint.setKey("xyzlast");
        entryPoint.setNonceValiditySeconds(99999);
        return entryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(digestAuthenticationEntryPoint())
             .and()
                .csrf().disable()
                //.httpBasic()
            //.and()
                .authorizeRequests()
                .antMatchers("/api/address/**", "/Api/Address/**").anonymous()
                .antMatchers("/api/apiKey/**").authenticated()
            .and()
                .addFilterAfter(digestAuthenticationFilter(), BasicAuthenticationFilter.class);


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
        // auth.inMemoryAuthentication().withUser("ykyoon").password("1234").roles("USER", "ADMIN");
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceBean());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    @Bean(name = BeanIds.USER_DETAILS_SERVICE)
    public UserDetailsService userDetailsServiceBean() throws Exception {
        CommonAppUserDetailService userDetailsService = new CommonAppUserDetailService();
        userDetailsService.setUserService(context.getBean(UserService.class));
        return userDetailsService;
    }

    @Bean
    public UserInfoHelper userInfoHelper() {
        UserInfoHelperImpl userInfoHelper = new UserInfoHelperImpl();
        userInfoHelper.setUserService(context.getBean(UserService.class));
        return userInfoHelper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
//                try {
                    return rawPassword.toString();
                    //return StringEncrypter.encrypt(rawPassword.toString());
//                } catch (UnsupportedEncodingException e) {
//                    return "";
//                }
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
//                return encodedPassword.equals(encode(rawPassword));
                return rawPassword.equals(encodedPassword);
            }
        };
    }
}
