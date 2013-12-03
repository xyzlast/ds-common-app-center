package co.kr.daesung.app.center.api.web.configs;

import co.kr.daesung.app.center.api.web.auth.*;
import co.kr.daesung.app.center.api.web.cors.CorsSupportFilter;
import co.kr.daesung.app.center.api.web.cors.CorsSupportLoginUrlAuthenticationEntryPoint;
import co.kr.daesung.app.center.domain.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
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
@ImportResource(value = "classpath:cxf-servlet.xml")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    public static final String CORS_SUPPORT_FILTER = "corsSupportFilter";
    public static final String ADMIN_ROLE = "ADMIN";

    @Autowired
    private ApplicationContext context;
    @Autowired
    private CorsSupportLoginUrlAuthenticationEntryPoint corsEntryPoint;

    /**
     * Digiest Authentication을 위한 Return filter
     * cors가 적용된 Digest 인증 필터로 교체
     * @return
     * @throws Exception
     */
    @Bean
    public DigestAuthenticationFilter digestAuthenticationFilter() throws Exception {
        DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
        digestAuthenticationFilter.setAuthenticationEntryPoint(corsEntryPoint);
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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().authenticationEntryPoint(corsEntryPoint)
             .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/admin/**").hasRole(ADMIN_ROLE)
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

    /**
     * AuthenticationManager를 LoginController에서 사용하기 위해서는 반드시, @Bean으로 등록되어야지 된다.
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * UserDetailService와 PasswordEncoder를 이용해서, AuthenticationProvider를 구성한다.
     * @return
     * @throws Exception
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceBean());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * UserDetailsService를 구성해준다. Bean Name은 반드시 BeanIds.USER_DETAILS_SERVICE로 등록되어야지 된다.
     * @return
     * @throws Exception
     */
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
