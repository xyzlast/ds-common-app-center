package co.kr.daesung.app.center.domain.entities.messages;

import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/11/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="apiKeyId")
    private ApiKey apiKey;

    @Column(name = "IsDuplicated")
    private boolean duplicated;
    @Column(name = "IsSent")
    private boolean sent;

    private Date createTime;
    private Date sentTime;

    @PrePersist
    public void initValues() {
        createTime = new Date();
    }
}
