package co.kr.daesung.app.center.domain.repo.messages;

import co.kr.daesung.app.center.domain.entities.messages.CrewMessage;
import co.kr.daesung.app.center.domain.entities.messages.QCrewMessage;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/12/13
 * Time: 4:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CrewMessageRepositorySupportImpl extends QueryDslRepositorySupport implements CrewMessageRepositorySupport {

    public CrewMessageRepositorySupportImpl() {
        super(CrewMessage.class);
    }

    @Override
    public long makeSentToAll() {
        return update(QCrewMessage.crewMessage)
                .set(QCrewMessage.crewMessage.sent, true)
                .set(QCrewMessage.crewMessage.sentTime, new Date())
                .execute();
    }
}
