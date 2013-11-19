package co.kr.daesung.app.center.api.web.aops;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 9:10 AM
 * To change this template use File | Settings | File Templates.
 */
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

public class AuthKeyCheckAdviceTest {

    @Test
    public void getHttpPattern() {
        // String patternString = "(?<httpUrl>((https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*))\\/?";
        String patternString = "(?<httpUrl>((https?:\\/\\/)([\\da-z\\.-]+)))\\/?";
        System.out.println(patternString);
        Pattern pattern = Pattern.compile(patternString);

        String sample1 = "http://stackoverflow.com/questions/415580/regex-named-groups-in-java";
        final Matcher matcher = pattern.matcher(sample1);
        assertThat(matcher.find(), is(true));
        assertThat(matcher.group("httpUrl"), is("http://stackoverflow.com"));
    }
}
