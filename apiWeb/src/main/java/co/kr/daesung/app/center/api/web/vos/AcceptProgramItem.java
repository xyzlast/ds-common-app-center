package co.kr.daesung.app.center.api.web.vos;

import co.kr.daesung.app.center.domain.entities.auth.AcceptProgram;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 6:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class AcceptProgramItem {
    private int id;
    private String program;
    private String description;
    private Date createTime;
    private Date lastCallTime;
    private long usedCount;

    public AcceptProgramItem() {

    }

    public AcceptProgramItem(AcceptProgram program) {
        id = program.getId();
        this.program = program.getProgram();
        description = program.getDescription();
        createTime = program.getCreateTime();
        lastCallTime = program.getLastCallTime();
        usedCount = program.getUsedCount();
    }
}
