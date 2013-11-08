package co.kr.daesung.app.center.domain.entities.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

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
    private Integer id;

    @OneToMany
    @JoinColumn(name = "apiKey")
    private ApiKey apiKey;

    private String program;
    private String description;
    private long usedCount;
    private boolean deleted;
    private Date createTime;
    private Date lastCallTime;

    public void increaseCallTime() {
        usedCount++;
        lastCallTime = new Date();
    }
}
