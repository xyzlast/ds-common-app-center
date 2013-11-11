package co.kr.daesung.app.center.domain.entities.messages;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/11/13
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "CrewMessage")
@Getter
@Setter
public class CrewMessage extends BaseMessage {
    private String toUserId;
    private String message;
    private String linkUrl;
}
