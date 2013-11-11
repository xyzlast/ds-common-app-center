package co.kr.daesung.app.center.domain.entities.auth;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/9/13
 * Time: 1:23 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "AcceptProgram")
@Getter
@Setter
public class AcceptProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "apiKeyId")
    private ApiKey apiKey;

    private String program;
    private String description;
    private long usedCount;
    private boolean deleted;
    private Date createTime;
    private Date lastCallTime;

    @PrePersist
    public void initValues() {
        createTime = new Date();
        lastCallTime = new Date();
    }

    public void increaseCallTime() {
        usedCount++;
        apiKey.setUsedCount(apiKey.getUsedCount() + 1L);
        lastCallTime = new Date();
    }
}
