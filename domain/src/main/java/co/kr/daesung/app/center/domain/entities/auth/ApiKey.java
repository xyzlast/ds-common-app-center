package co.kr.daesung.app.center.domain.entities.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    @Column(length = 36)
    private String id;

    @Column(name="userId", length = 20)
    private String userId;

    private Date createTime;
    @Column(name = "Deleted", nullable = false)
    private boolean deleted;
    private long usedCount;

    @JsonIgnore
    @OneToMany(mappedBy = "apiKey", cascade = { CascadeType.ALL })
    private List<AcceptProgram> acceptPrograms = new ArrayList<>();

    @PrePersist
    public void initPersistValues() {
        id = UUID.randomUUID().toString();
        createTime = new Date();
        deleted = false;
        usedCount = 0L;
    }

    public void addProgram(String programName, String description) {
        AcceptProgram acceptProgram = new AcceptProgram();
        acceptProgram.setProgram(programName);
        acceptProgram.setDescription(description);
        acceptPrograms.add(acceptProgram);
    }
}
