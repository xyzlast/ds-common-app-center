package co.kr.daesung.app.center.domain.repo.auth;

import co.kr.daesung.app.center.domain.entities.auth.User;
import co.kr.daesung.app.center.domain.repo.BaseRepository;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserRepository extends BaseRepository<User, Long> {
    User findByUsername(String username);
}
