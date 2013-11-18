package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.auth.User;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 4:44 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {
    User findByUsername(String username);
    User addNewUser(String username, String name, String password, String[] roles);
    User disableUser(String username);
}
