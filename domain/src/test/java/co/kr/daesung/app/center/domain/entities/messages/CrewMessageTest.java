package co.kr.daesung.app.center.domain.entities.messages;

import org.junit.Test;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/12/13
 * Time: 10:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class CrewMessageTest {

    private static final String TO_USER_ID = "201105010";
    private static final String MESSAGE = "TEST_MESSAGE";
    private static final String LINK_URL = "http://github.com";

    @Test
    public void testGetBytesData() throws Exception {
        CrewMessage crewMessage = new CrewMessage();
        crewMessage.setToUserId(TO_USER_ID);
        crewMessage.setMessage(MESSAGE);
        crewMessage.setLinkUrl(LINK_URL);

        byte[] bytesData = crewMessage.convertToBytesData();
        assertThat(bytesData.length, is(not(0)));
    }
}
