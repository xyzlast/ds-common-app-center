package co.kr.daesung.app.center.domain.services

import co.kr.daesung.app.center.domain.configs.DomainConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * Created by ykyoon on 12/11/13.
 */
@ContextConfiguration(classes = [ DomainConfiguration ])
class UserServiceImplTest extends Specification {
    @Autowired
    UserService userService

    def setup() {
    }

    def '사용자 이름으로 사용자 찾기, 없는 경우에 null 반환'() {
        expect:
        def user =  userService.findByUsername(username)
        (user != null) == result
        where:
        username | result
        'ykyoon' | true
        'noname' | false
        '말도안되는 이름' | false
    }
}
