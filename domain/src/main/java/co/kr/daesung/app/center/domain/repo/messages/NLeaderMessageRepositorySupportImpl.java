package co.kr.daesung.app.center.domain.repo.messages;

import co.kr.daesung.app.center.domain.entities.messages.NLeaderMessage;
import co.kr.daesung.app.center.domain.entities.messages.QNLeaderMessage;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/12/13
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class NLeaderMessageRepositorySupportImpl extends QueryDslRepositorySupport implements NLeaderMessageRepositorySupport {

    public NLeaderMessageRepositorySupportImpl() {
        super(NLeaderMessage.class);
    }

    @Override
    public long makeSentToAll() {
        return update(QNLeaderMessage.nLeaderMessage)
                .set(QNLeaderMessage.nLeaderMessage.sent, true)
                .set(QNLeaderMessage.nLeaderMessage.sentTime, new Date())
                .execute();
    }
}
