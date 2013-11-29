package co.kr.daesung.app.center.api.web.configs;

import co.kr.daesung.app.center.api.web.auth.*;
import co.kr.daesung.app.center.api.web.cors.CorsSupportFilter;
import co.kr.daesung.app.center.api.web.cors.CorsSupportLoginUrlAuthenticationEntryPoint;
import co.kr.daesung.app.center.domain.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

import javax.servlet.Filter;

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
@ComponentScan("co.kr.daesung.app.center.api.web.cors")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    public static final String CORS_SUPPORT_FILTER = "corsSupportFilter";

    @Autowired
    private ApplicationContext context;
    @Autowired
    private CorsSupportLoginUrlAuthenticationEntryPoint corsEntryPoint;

    @Bean
    public DigestAuthenticationFilter digestAuthenticationFilter() throws Exception {
        DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
        digestAuthenticationFilter.setAuthenticationEntryPoint(digestAuthenticationEntryPoint());
        digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());
        return digestAuthenticationFilter;
    }

    @Bean
    public LoginProcessHandler loginProcessHandler() {
        LoginProcessHandler loginProcessHandler = new LoginProcessHandler();
        loginProcessHandler.setObjectMapper(objectMapper());
        return loginProcessHandler;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper;
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
        http
                //.exceptionHandling().authenticationEntryPoint(digestAuthenticationEntryPoint())
                .exceptionHandling().authenticationEntryPoint(corsEntryPoint)
             .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/notice/list").anonymous()
                .antMatchers("/api/address/**", "/Api/Address/**").anonymous()
                .antMatchers("/api/apiKey/**").authenticated()
                .antMatchers("/api/message/**").authenticated()
                .antMatchers("/api/auth/**").authenticated()
            .and()
                .addFilterAfter(digestAuthenticationFilter(), BasicAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
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
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                    return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.equals(encodedPassword);
            }
        };
    }

    @Bean(name = CORS_SUPPORT_FILTER)
    public Filter corsSupportFilter() {
        CorsSupportFilter corsSupportFilter = new CorsSupportFilter();
        return corsSupportFilter;
    }
}
