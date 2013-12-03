package co.kr.daesung.app.center.api.web.tasks;

import co.kr.daesung.app.center.domain.entities.messages.CrewMessage;
import co.kr.daesung.app.center.domain.entities.messages.NLeaderMessage;
import co.kr.daesung.app.center.domain.services.MessageSender;
import co.kr.daesung.app.center.domain.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 1:14 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class MessageSenderTask {
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private MessageService messageService;

    private Object crewMessageLock = new Object();
    private Object nleaderMessageLock = new Object();

    @Scheduled(fixedRate = 60 * 1000)
    public void sendCrewMessages() {
       synchronized (crewMessageLock) {
           List<CrewMessage> crewMessagesInQueue = messageService.getCrewMessagesInQueue(false);
           messageSender.sendCrewMessages(crewMessagesInQueue);
       }
    }

    @Scheduled(fixedRate = 60 * 1000)
    public void sendNLeaderMessages() {
        synchronized (nleaderMessageLock) {
            List<NLeaderMessage> nLeaderMessagesInQueue = messageService.getNLeaderMessagesInQueue(false);
            messageSender.sendNLeaderMessages(nLeaderMessagesInQueue);
        }
    }
}
