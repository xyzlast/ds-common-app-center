package co.kr.daesung.app.center.api.web.ws;

import javax.jws.WebService;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 12/2/13
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
@WebService
public interface HelloWorld {
    String sayHi(String name);
}
