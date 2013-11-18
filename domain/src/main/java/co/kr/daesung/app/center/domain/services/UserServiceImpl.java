package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.auth.User;
import co.kr.daesung.app.center.domain.repo.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User addNewUser(String username, String name, String password, String[] roles) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User disableUser(String username) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
