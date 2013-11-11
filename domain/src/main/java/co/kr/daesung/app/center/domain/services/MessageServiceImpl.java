package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.messages.CrewMessage;
import co.kr.daesung.app.center.domain.entities.messages.NLeaderMessage;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public CrewMessage addMessageToQueue(String ApiKey, String fromUser, String toUser, String message, String linkUrl, boolean force) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int sendCrewMessagesFromQueue(int maxItemCount) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
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
    public NLeaderMessage addNLeaderMessageToQueue(String apiKey, String fromUserId, String toUserId, String title, String content, boolean force) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int sendNLeaderMessagesFromQueue(int maxItemCount) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
