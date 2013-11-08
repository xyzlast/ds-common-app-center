package co.kr.daesung.app.center.domain.entities.auth;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/9/13
 * Time: 1:25 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ApiKey")
@Getter
@Setter
public class ApiKey {
    @Id
    @GeneratedValue(generator = "msGuid")
    @Column(length = 32)
    private String id;

    @Column(name="userId", length = 20)
    private String userId;

    private Date createTime;
    private boolean deleted;
    private long usedCount;

    @OneToMany(mappedBy = "apiKey")
    private List<AcceptProgram> acceptPrograms = new ArrayList<>();

    @PrePersist
    public void initPersistValues() {
        createTime = new Date();
        deleted = false;
        usedCount = 0L;
    }
}
