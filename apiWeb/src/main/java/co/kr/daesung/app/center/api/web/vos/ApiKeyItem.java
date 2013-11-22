package co.kr.daesung.app.center.api.web.vos;

import co.kr.daesung.app.center.domain.entities.auth.AcceptProgram;
import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class ApiKeyItem {
    private String id;
    private long usedCount;
    private Date createTime;
    private List<AcceptProgramItem> programs;
    public ApiKeyItem() {

    }

    public ApiKeyItem(ApiKey apiKey, List<AcceptProgram> acceptPrograms) {
        id = apiKey.getId();
        usedCount = apiKey.getUsedCount();
        createTime = apiKey.getCreateTime();
        programs = new ArrayList<>();

        for(AcceptProgram program : acceptPrograms) {
            programs.add(new AcceptProgramItem(program));
        }
    }
}
