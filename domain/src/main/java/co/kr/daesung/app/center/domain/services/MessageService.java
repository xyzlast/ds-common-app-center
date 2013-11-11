package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.messages.CrewMessage;
import co.kr.daesung.app.center.domain.entities.messages.NLeaderMessage;
import org.springframework.data.domain.Page;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/11/13
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MessageService {
    CrewMessage addMessageToQueue(String ApiKey, String fromUser, String toUser, String message,
                                  String linkUrl, boolean force);
    int sendCrewMessagesFromQueue(int maxItemCount);
    Page<CrewMessage> getCrewMessages(String apiKeyId, int pageIndex, int pageSize);
    Page<CrewMessage> getCrewMessages(int pageIndex, int pageSize);

    NLeaderMessage addNLeaderMessageToQueue(String apiKey, String fromUserId, String toUserId, String title,
                                       String content, boolean force);
    int sendNLeaderMessagesFromQueue(int maxItemCount);

}
