package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.messages.CrewMessage;
import co.kr.daesung.app.center.domain.entities.messages.NLeaderMessage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/11/13
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MessageSender {
    int sendCrewMessages(List<CrewMessage> crewMessages);
    int sendNLeaderMessages(List<NLeaderMessage> nLeaderMessages);
}
