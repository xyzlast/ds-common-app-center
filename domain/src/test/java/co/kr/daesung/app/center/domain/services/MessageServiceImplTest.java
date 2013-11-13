package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import co.kr.daesung.app.center.domain.entities.messages.CrewMessage;
import co.kr.daesung.app.center.domain.entities.messages.NLeaderMessage;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/11/13
 * Time: 5:21 PM
 * To change this template use File | Settings | File Templates.
 */
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DomainConfiguration.class })
public class MessageServiceImplTest {
    @Autowired
    private MessageService messageService;
    @Autowired
    private ApiKeyService apiKeyService;

    private static final String TO_USER_ID = "201105010";
    private static final String FROM_USER_ID = "201105010";
//    private static final String TO_USER_ID = "201210107";
//    private static final String FROM_USER_ID = "201210107";
    private String apiId;

    @Before
    public void setUp() {
        ApiKey apiKey = apiKeyService.getTopUsedApiKeys(0, 1).getContent().get(0);
        apiId = apiKey.getId();
        assertThat(apiId, is(not(nullValue())));
    }

    @Test
    public void testAddCrewMessageToQueue() throws Exception {
        String message = "TEST_MESSAGE";
        String linkUrl = "http://www.github.com";

        CrewMessage message1 = messageService.addCrewMessageToQueue(apiId, FROM_USER_ID, TO_USER_ID, message, linkUrl, true);
        assertThat(message1, is(not(nullValue())));
        assertThat(message1.isDuplicated(), is(false));

        CrewMessage message2 = messageService.addCrewMessageToQueue(apiId, FROM_USER_ID, TO_USER_ID, message, linkUrl, false);
        assertThat(message2, is(not(nullValue())));
        assertThat(message2.isDuplicated(), is(true));

        CrewMessage message3 = messageService.addCrewMessageToQueue(apiId, FROM_USER_ID, TO_USER_ID, message, linkUrl, true);
        assertThat(message3, is(not(nullValue())));
        assertThat(message3.isDuplicated(), is(false));
    }

    @Test
    public void testGetCrewMessagesInQueue() throws Exception {
        testAddCrewMessageToQueue();
        List<CrewMessage> crewMessages = messageService.getCrewMessagesInQueue(true);
        assertThat(crewMessages.size(), is(not(0)));

        crewMessages = messageService.getCrewMessagesInQueue(false);
        assertThat(crewMessages.size(), is(not(0)));

        for(CrewMessage crewMessage : crewMessages) {
            if(crewMessage.isDuplicated()) {
                assertThat("it contains duplicated result", false, is(true));
            }
        }
    }

    @Test
    @Transactional
    public void testSendCrewMessagesFromQueue() throws Exception {
        messageService.makeAllToSent();

        testAddCrewMessageToQueue();
        testAddCrewMessageToQueue();
        int itemCount = messageService.sendCrewMessagesFromQueue();
        assertThat(itemCount, is(4));
    }

    @Test
    public void testGetCrewMessages1() throws Exception {
        final Page<CrewMessage> crewMessages = messageService.getCrewMessages(0, 100);
        assertThat(crewMessages.getNumber(), is(0));
    }

    @Test
    public void testGetCrewMessages2() throws Exception {
        final Page<CrewMessage> crewMessages = messageService.getCrewMessages(apiId, 0, 100);
        assertThat(crewMessages.getNumber(), is(0));
    }

    @Test
    @Rollback(false)
    public void testAddNLeaderMessageToQueue() throws Exception {
        final NLeaderMessage nLeaderMessage = messageService.addNLeaderMessageToQueue(apiId, FROM_USER_ID, TO_USER_ID, "TITLE", "CONTENT", true);
        assertThat(nLeaderMessage, is(not(nullValue())));
        assertThat(nLeaderMessage.getId(), is(not(0)));

        final NLeaderMessage nLeaderMessage2 = messageService.addNLeaderMessageToQueue(apiId, FROM_USER_ID, TO_USER_ID, "TITLE", "CONTENT", false);
        assertThat(nLeaderMessage2, is(not(nullValue())));
        assertThat(nLeaderMessage2.getId(), is(not(0)));
        assertThat(nLeaderMessage2.isDuplicated(), is(true));

        final NLeaderMessage nLeaderMessage3 = messageService.addNLeaderMessageToQueue(apiId, FROM_USER_ID, TO_USER_ID, "TITLE", "CONTENT", true);
        assertThat(nLeaderMessage3, is(not(nullValue())));
        assertThat(nLeaderMessage3.getId(), is(not(0)));
        assertThat(nLeaderMessage3.isDuplicated(), is(false));

        messageService.makeAllToSent();
    }

    @Test
    public void testGetNLeaderMessagesInQueue() throws Exception {
        messageService.makeAllToSent();
        final List<NLeaderMessage> nLeaderMessagesInQueue = messageService.getNLeaderMessagesInQueue(true);
        assertThat(nLeaderMessagesInQueue.size(), is(0));

        final NLeaderMessage nLeaderMessage = messageService.addNLeaderMessageToQueue(apiId, FROM_USER_ID, TO_USER_ID, "TITLE", "CONTENT", true);
        assertThat(nLeaderMessage, is(not(nullValue())));
        assertThat(nLeaderMessage.getId(), is(not(0)));
        messageService.makeAllToSent();
    }

    @Test
    public void testMakeSentToAll() throws Exception {
        messageService.makeAllToSent();
        final List<NLeaderMessage> nLeaderMessagesInQueue = messageService.getNLeaderMessagesInQueue(true);
        assertThat(nLeaderMessagesInQueue.size(), is(0));

        final NLeaderMessage nLeaderMessage = messageService.addNLeaderMessageToQueue(apiId, FROM_USER_ID, TO_USER_ID, "TITLE", "CONTENT", true);
        assertThat(nLeaderMessage, is(not(nullValue())));
        assertThat(nLeaderMessage.getId(), is(not(0)));

        final NLeaderMessage nLeaderMessage2 = messageService.addNLeaderMessageToQueue(apiId, FROM_USER_ID, TO_USER_ID, "TITLE", "CONTENT", true);
        assertThat(nLeaderMessage2, is(not(nullValue())));
        assertThat(nLeaderMessage2.getId(), is(not(0)));
        assertThat(nLeaderMessage2.isDuplicated(), is(false));

        final NLeaderMessage nLeaderMessage3 = messageService.addNLeaderMessageToQueue(apiId, FROM_USER_ID, TO_USER_ID, "TITLE", "CONTENT", true);
        assertThat(nLeaderMessage3, is(not(nullValue())));
        assertThat(nLeaderMessage3.getId(), is(not(0)));
        assertThat(nLeaderMessage3.isDuplicated(), is(false));


        final List<NLeaderMessage> nLeaderMessagesInQueue2 = messageService.getNLeaderMessagesInQueue(true);
        assertThat(nLeaderMessagesInQueue2.size(), is(3));
        messageService.makeAllToSent();

        final List<NLeaderMessage> nLeaderMessagesInQueue3 = messageService.getNLeaderMessagesInQueue(true);
        assertThat(nLeaderMessagesInQueue3.size(), is(0));
    }
}
