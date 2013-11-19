package co.kr.daesung.app.center.api.web.auth;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import co.kr.daesung.app.center.api.web.configs.ControllerConfiguration;
import co.kr.daesung.app.center.api.web.configs.SecurityConfiguration;
import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.entities.auth.User;
import co.kr.daesung.app.center.domain.services.UserService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DomainConfiguration.class, SecurityConfiguration.class, ControllerConfiguration.class})
@WebAppConfiguration
public class UserInfoHelperImplTest {
    private UserInfoHelper userInfoHelper;
    @Autowired
    private UserService userService;
    private static final String USER_NAME = "ykyoon";

    @Before
    public void setUp() {
        UserInfoHelperImpl userInfoHelper = new UserInfoHelperImpl();
        userInfoHelper.setUserService(userService);
        this.userInfoHelper = userInfoHelper;
    }

    @Test
    public void getUserName() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        stub(request.getHeader(UserInfoHelperImpl.AUTH_HEADER)).toReturn("Basic " + getEncodedString());
        User user = userInfoHelper.getUserFromRequest(request);
        assertThat(user.getUsername(), is(USER_NAME));
    }

    private String getEncodedString() throws Exception {
        String password = "1234";
        String header = "Authorization";
        String authHeaderFormat = "Basic ";
        String encodingRawData = String.format("%s:%s", USER_NAME, password);

        return new String(Base64.encode(encodingRawData.getBytes("utf-8")));
    }
}
