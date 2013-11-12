package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.messages.CrewMessage;
import co.kr.daesung.app.center.domain.entities.messages.NLeaderMessage;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/11/13
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MessageService {
    CrewMessage addCrewMessageToQueue(String apiKey,String fromUserId, String toUser, String message,
                                  String linkUrl, boolean force);
    List<CrewMessage> getCrewMessagesInQueue(boolean includeDuplicated);
    int sendCrewMessagesFromQueue();

    Page<CrewMessage> getCrewMessages(String apiKeyId, int pageIndex, int pageSize);
    Page<CrewMessage> getCrewMessages(int pageIndex, int pageSize);

    NLeaderMessage addNLeaderMessageToQueue(String apiKey, String fromUserId, String toUserId, String title,
                                       String content, boolean force);
    List<NLeaderMessage> getNLeaderMessagesInQueue(boolean includeDuplicated);
    int sendNLeaderMessagesFromQueue();

}
