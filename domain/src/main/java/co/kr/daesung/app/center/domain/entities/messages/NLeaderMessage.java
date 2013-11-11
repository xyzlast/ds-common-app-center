package co.kr.daesung.app.center.domain.entities.messages;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/11/13
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "NLeaderMessage")
@Getter
@Setter
public class NLeaderMessage extends BaseMessage {
    private String fromUserId;
    private String toUserId;
    private String title;
    private String content;
}
