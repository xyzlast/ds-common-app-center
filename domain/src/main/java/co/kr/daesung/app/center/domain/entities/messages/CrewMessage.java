package co.kr.daesung.app.center.domain.entities.messages;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.UnsupportedEncodingException;

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
    private String fromUserId;
    private String toUserId;
    private String message;
    private String linkUrl;

    private static final String MESSAGE_HEADER = "SYSMSG";
    private static final String MESSAGE_TYPE = "SYSTEMALERT";

    public byte[] convertToBytesData() {
        String messageString = String.format("%s\t%s\t%s\t%s\t%s", MESSAGE_HEADER, toUserId, MESSAGE_TYPE, message, linkUrl);
        try {
            return messageString.getBytes("MS949");
        } catch (UnsupportedEncodingException e) {
            return new byte[0];
        }
    }
}
