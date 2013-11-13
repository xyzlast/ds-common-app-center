package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import co.kr.daesung.app.center.domain.entities.messages.CrewMessage;
import co.kr.daesung.app.center.domain.entities.messages.NLeaderMessage;
import co.kr.daesung.app.center.domain.entities.messages.QCrewMessage;
import co.kr.daesung.app.center.domain.entities.messages.QNLeaderMessage;
import co.kr.daesung.app.center.domain.repo.auth.ApiKeyRepository;
import co.kr.daesung.app.center.domain.repo.messages.CrewMessageRepository;
import co.kr.daesung.app.center.domain.repo.messages.CrewMessageRepositorySupport;
import co.kr.daesung.app.center.domain.repo.messages.NLeaderMessageRepository;
import co.kr.daesung.app.center.domain.repo.messages.NLeaderMessageRepositorySupport;
import co.kr.daesung.app.center.domain.utils.ArrayUtil;
import com.mysema.query.Query;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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

    @Autowired
    private CrewMessageRepositorySupport crewMessageRepositorySupport;
    @Autowired
    private NLeaderMessageRepositorySupport nLeaderMessageRepositorySupport;

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
        int count = messageSender.sendCrewMessages(crewMessages);
        crewMessageRepository.save(crewMessages);
        return count;
    }

    @Override
    public Page<CrewMessage> getCrewMessages(String apiKeyId, int pageIndex, int pageSize) {
        ApiKey apiKey = apiKeyRepository.findOne(apiKeyId);
        if(apiKey == null) {
            throw new IllegalArgumentException("api key is not found!");
        }
        QCrewMessage qCrewMessage = QCrewMessage.crewMessage;
        Predicate predicate = qCrewMessage.apiKey.eq(apiKey);
        PageRequest pageRequest = new PageRequest(pageIndex, pageSize, Sort.Direction.DESC, "createTime");
        return crewMessageRepository.findAll(predicate, pageRequest);
    }

    @Override
    public Page<CrewMessage> getCrewMessages(int pageIndex, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageIndex, pageSize, Sort.Direction.DESC, "createTime");
        return crewMessageRepository.findAll(pageRequest);
    }

    @Override
    public NLeaderMessage addNLeaderMessageToQueue(String apiKeyId, String fromUserId, String toUserId,
                                                   String title, String content, boolean force) {
        ApiKey apiKey = apiKeyRepository.findOne(apiKeyId);
        if(apiKey == null) {
            throw new IllegalArgumentException("api key is not found!");
        }
        NLeaderMessage nLeaderMessage = new NLeaderMessage();
        nLeaderMessage.setFromUserId(fromUserId);
        nLeaderMessage.setToUserId(toUserId);
        nLeaderMessage.setTitle(title);
        nLeaderMessage.setContent(content);
        boolean duplicated = false;
        if(!force) {
            QNLeaderMessage qNLeaderMessage = QNLeaderMessage.nLeaderMessage;
            Date d = getRangeTime();
            Predicate predicate = qNLeaderMessage.title.eq(title)
                    .and(qNLeaderMessage.fromUserId.eq(fromUserId))
                    .and(qNLeaderMessage.toUserId.eq(toUserId))
                    .and(qNLeaderMessage.createTime.after(d));
            duplicated = nLeaderMessageRepository.count(predicate) != 0L;
        }
        nLeaderMessage.setDuplicated(duplicated);
        nLeaderMessageRepository.save(nLeaderMessage);
        return nLeaderMessage;
    }

    @Override
    public List<NLeaderMessage> getNLeaderMessagesInQueue(boolean includeDuplicated) {
        QNLeaderMessage qnLeaderMessage = QNLeaderMessage.nLeaderMessage;
        Date d = getRangeTime();
        Predicate predicate;
        if(includeDuplicated) {
            predicate = qnLeaderMessage.sent.isFalse()
                    .and(qnLeaderMessage.createTime.after(d));
        } else {
            predicate = qnLeaderMessage.sent.isFalse()
                    .and(qnLeaderMessage.duplicated.isFalse())
                    .and(qnLeaderMessage.createTime.after(d));
        }
        PageRequest pageRequest = new PageRequest(0, sendMaxCount, Sort.Direction.ASC, "createTime");
        return nLeaderMessageRepository.findAll(predicate, pageRequest).getContent();
    }

    @Override
    public int sendNLeaderMessagesFromQueue() {
        List<NLeaderMessage> nLeaderMessages = getNLeaderMessagesInQueue(false);
        int itemCount = messageSender.sendNLeaderMessages(nLeaderMessages);
        nLeaderMessageRepository.save(nLeaderMessages);
        return itemCount;
    }

    @Override
    public long makeAllToSent() {
        final long crewMessengerDeleteCount = crewMessageRepositorySupport.makeSentToAll();
        final long nLeaderMessageDeleteCount = nLeaderMessageRepositorySupport.makeSentToAll();

        return crewMessengerDeleteCount + nLeaderMessageDeleteCount;
    }
}
