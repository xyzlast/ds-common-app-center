package co.kr.daesung.app.center.api.web.auth;

import co.kr.daesung.app.center.domain.entities.auth.User;
import co.kr.daesung.app.center.domain.services.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class CommonAppUserDetailService implements UserDetailsService {
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userService.findByUsername(username);
            List<SimpleGrantedAuthority> roles = new ArrayList<>();
            for(String role : user.getRoles()) {
                roles.add(new SimpleGrantedAuthority(role));
            }
            UserDetails userDetails = new org.springframework.security.core
                    .userdetails.User(user.getUsername(), user.getPassword(), roles);
            return userDetails;
        } catch(Exception ex) {
            throw new UsernameNotFoundException("username is not found!");
        }
    }
}
