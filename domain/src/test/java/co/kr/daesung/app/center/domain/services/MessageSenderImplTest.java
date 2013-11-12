package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.entities.messages.CrewMessage;
import co.kr.daesung.app.center.domain.entities.messages.NLeaderMessage;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/12/13
 * Time: 10:16 AM
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( classes = { DomainConfiguration.class })
public class MessageSenderImplTest {

    private static final String TO_USER_ID = "201105010";
    private static final String MESSAGE = "TEST_MESSAGE";
    private static final String LINK_URL = "http://github.com";

    @Autowired
    private MessageSender messageSender;

    @Test
    public void testSendCrewMessages() throws Exception {
        List<CrewMessage> crewMessages = new ArrayList<>();
        for(int i = 0 ; i < 2 ; i++) {
            crewMessages.add(generateCrewMessage());
        }
        int itemCount = messageSender.sendCrewMessages(crewMessages);
        assertThat(itemCount, is(crewMessages.size()));

        for(CrewMessage crewMessage : crewMessages) {
            assertThat(crewMessage.isSent(), is(true));
        }
    }

    private CrewMessage generateCrewMessage() {
        CrewMessage crewMessage = new CrewMessage();
        crewMessage.setToUserId(TO_USER_ID);
        crewMessage.setMessage(MESSAGE);
        crewMessage.setLinkUrl(LINK_URL);
        return crewMessage;
    }

    @Test
    public void testSendNLeaderMessages() throws Exception {
        NLeaderMessage nLeaderMessage = new NLeaderMessage();
        nLeaderMessage.setToUserId(TO_USER_ID);
        nLeaderMessage.setFromUserId(TO_USER_ID);
        nLeaderMessage.setTitle("TEST_TITLE");
        nLeaderMessage.setContent("TEST_CONTENT");

        List<NLeaderMessage> messages = new ArrayList<>();
        messages.add(nLeaderMessage);
        int itemCount = messageSender.sendNLeaderMessages(messages);
        assertThat(itemCount, is(messages.size()));

        for(NLeaderMessage message : messages) {
            assertThat(message.isSent(), is(true));
        }
    }


}
