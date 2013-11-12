package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import co.kr.daesung.app.center.domain.entities.messages.CrewMessage;
import co.kr.daesung.app.center.domain.entities.messages.NLeaderMessage;
import co.kr.daesung.app.center.domain.entities.messages.QCrewMessage;
import co.kr.daesung.app.center.domain.repo.auth.ApiKeyRepository;
import co.kr.daesung.app.center.domain.repo.messages.CrewMessageRepository;
import co.kr.daesung.app.center.domain.repo.messages.NLeaderMessageRepository;
import co.kr.daesung.app.center.domain.utils.ArrayUtil;
import com.mysema.query.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/11/13
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    private NLeaderMessageRepository nLeaderMessageRepository;
    @Autowired
    private CrewMessageRepository crewMessageRepository;
    @Autowired
    private ApiKeyRepository apiKeyRepository;
    @Autowired
    private MessageSender messageSender;

    @Value("${message.duplicated.minutes}")
    private int duplicatedMinutes;
    @Value("${message.send.max.count}")
    private int sendMaxCount;

    @Override
    public CrewMessage addCrewMessageToQueue(String apiKey, String fromUserId, String toUser, String message,
                                         String linkUrl, boolean force) {
        final ApiKey apiKeyItem = apiKeyRepository.findOne(apiKey);
        CrewMessage crewMessage = new CrewMessage();
        crewMessage.setApiKey(apiKeyItem);
        crewMessage.setFromUserId(fromUserId);
        crewMessage.setToUserId(toUser);
        crewMessage.setMessage(message);
        crewMessage.setLinkUrl(linkUrl);
        boolean duplicated = false;

        if(!force) {
            QCrewMessage qCrewMessage = QCrewMessage.crewMessage;
            Date d = getRangeTime();

            Predicate predicate = qCrewMessage.fromUserId.eq(fromUserId)
                    .and(qCrewMessage.toUserId.eq(toUser))
                    .and(qCrewMessage.message.eq(message))
                    .and(qCrewMessage.linkUrl.eq(linkUrl))
                    .and(qCrewMessage.createTime.after(d));
            duplicated = crewMessageRepository.count(predicate) != 0L;
        }
        crewMessage.setDuplicated(duplicated);
        crewMessageRepository.save(crewMessage);
        return crewMessage;
    }

    private Date getRangeTime() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MINUTE, -duplicatedMinutes);
        Date d = c.getTime();
        return d;
    }

    @Override
    public List<CrewMessage> getCrewMessagesInQueue(boolean includeDuplicated) {
        QCrewMessage qCrewMessage = QCrewMessage.crewMessage;
        Date d = getRangeTime();
        Predicate predicate;
        if(includeDuplicated) {
            predicate = qCrewMessage.sent.isFalse()
                    .and(qCrewMessage.createTime.after(d));
        } else {
            predicate = qCrewMessage.sent.isFalse()
                    .and(qCrewMessage.duplicated.isFalse())
                    .and(qCrewMessage.createTime.after(d));
        }
        PageRequest pageRequest = new PageRequest(0, sendMaxCount, Sort.Direction.ASC, "createTime");
        return crewMessageRepository.findAll(predicate, pageRequest).getContent();
    }

    @Override
    public int sendCrewMessagesFromQueue() {
        List<CrewMessage> crewMessages = getCrewMessagesInQueue(false);
        messageSender.sendCrewMessages(crewMessages);
        crewMessageRepository.save(crewMessages);
        int count = 0;
        for(CrewMessage crewMessage : crewMessages) {
            if(crewMessage.isSent()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Page<CrewMessage> getCrewMessages(String apiKeyId, int pageIndex, int pageSize) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Page<CrewMessage> getCrewMessages(int pageIndex, int pageSize) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public NLeaderMessage addNLeaderMessageToQueue(String apiKey, String fromUserId, String toUserId,
                                                   String title, String content, boolean force) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<NLeaderMessage> getNLeaderMessagesInQueue(boolean includeDuplicated) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int sendNLeaderMessagesFromQueue() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
