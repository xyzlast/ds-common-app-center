package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.messages.CrewMessage;
import co.kr.daesung.app.center.domain.entities.messages.NLeaderMessage;
import com.mysema.commons.lang.Assert;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/11/13
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Component
public class MessageSenderImpl implements MessageSender {

    @Value("${message.crew.server.address}")
    private String crewMessageServerIp;
    @Value("${message.crew.server.port}")
    private int crewMessageServerPort;
    @Value("${message.crew.sleep}")
    private int crewMessageSleepTime;


    private static final String SELECT_USER_QUERY = "SELECT USERNUM, NAME FROM WBUSER WHERE USERID = ?";
    private static final String MAX_MAIL_MESSAGE_COUNT_QUERY = "SELECT max(RWID) FROM MAILBOX_FOLDER WHERE USERNUM=?";
    private static final String INSERT_MAILBOX_FOLDER_QUERY =
    "INSERT INTO MAILBOX_FOLDER(USERNUM, MAILBOXID, RWID, SENDERID, TITLE, RECEIVEDDATE, SENDEDDATE, VIEWEDDATE, ISVIEWED, PRIORITY, ISINSIDE, MSGSIZE, MESSAGEID, CONTENT, RECEIVERID, CC, BCC, HEADER) " +
                        "VALUES(?,       ?,         ?,    ?,        ?,     ?,            ?,          '',         0,        0,        1,        ?,       '',        ?,       ?,          '', '',  ?)";
    private static final String NLEADER_MESSAGE_HEADER = "";
    private static final String RECEIVER_ID_FORMAT = "%s$$Sep$$%s";

    @Autowired
    private JdbcTemplate nleaderJdbcTemplate;

    @Override
    public int sendCrewMessages(List<CrewMessage> crewMessages) {
        int itemCount = 0;
        Date sentTime = new Date();
        try {
            Socket socket = new Socket(crewMessageServerIp, crewMessageServerPort);
            OutputStream outputStream = socket.getOutputStream();
            for(CrewMessage crewMessage : crewMessages) {
                try {
                    outputStream.write(crewMessage.convertToBytesData());
                    crewMessage.setSent(true);
                    crewMessage.setSentTime(sentTime);
                    itemCount++;
                    Thread.sleep(crewMessageSleepTime);
                } catch(IOException | InterruptedException ex) {
                    log.error("socket send exception", ex);
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemCount;
    }

    @Override
    public int sendNLeaderMessages(List<NLeaderMessage> nLeaderMessages) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date sentTime = new Date();
        String timeString = dateFormat.format(c.getTime());

        int sentCount = 0;
        for(NLeaderMessage message : nLeaderMessages) {
            try {
                SelectUserQueryResult userInforResult = nleaderJdbcTemplate.query(SELECT_USER_QUERY, new Object[]{message.getToUserId()}, new SelectUserQueryExtractor());
                if(userInforResult == null) {
                    continue;
                }
                int maxMailCount = nleaderJdbcTemplate.queryForObject(MAX_MAIL_MESSAGE_COUNT_QUERY, new Object[] { userInforResult.getToUserNumber() }, Integer.class);
                if(maxMailCount == 0) {
                    continue;
                }
                String messageTitle = message.getTitle().replace("'", "`");
                String messageContext = message.getContent().replace("'", "`");
                String receiverId = String.format(RECEIVER_ID_FORMAT, userInforResult.getToUserName(), userInforResult.getToUserName());
                int messageLength = messageContext.length();
                nleaderJdbcTemplate.update(INSERT_MAILBOX_FOLDER_QUERY,
                        new Object[] {
                                userInforResult.getToUserNumber(),
                                "0",
                                maxMailCount + 1,
                                message.getFromUserId(),
                                messageTitle,
                                timeString,
                                timeString,
                                messageLength,
                                messageContext,
                                receiverId,
                                NLEADER_MESSAGE_HEADER
                        });
                message.setSent(true);
                message.setSentTime(sentTime);
                sentCount++;
            } catch(Exception ex) {
                log.error("NLeader DB error : ", ex);
            }
        }
        return sentCount;
    }

    @Getter
    @Setter
    private class SelectUserQueryResult {
        public SelectUserQueryResult(String toUserNumber, String toUserName) {
            this.toUserNumber = toUserNumber;
            this.toUserName = toUserName;
        }

        private String toUserNumber;
        private String toUserName;
    }

    private class SelectUserQueryExtractor implements ResultSetExtractor<SelectUserQueryResult> {
        @Override
        public SelectUserQueryResult extractData(ResultSet rs) throws SQLException, DataAccessException {
            while(rs.next()) {
                SelectUserQueryResult result = new SelectUserQueryResult(rs.getString(1), rs.getString(2));
                return result;
            }
            return null;
        }
    }

}
