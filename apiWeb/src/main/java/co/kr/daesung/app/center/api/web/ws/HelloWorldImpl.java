package co.kr.daesung.app.center.api.web.ws;

import javax.jws.WebService;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 12/2/13
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */
@WebService(endpointInterface = "co.kr.daesung.app.center.api.web.ws.HelloWorld")
public class HelloWorldImpl implements HelloWorld {
    @Override
    public String sayHi(String name) {
        System.out.println("SayHi..");
        return "Hello " + name;
    }
}
