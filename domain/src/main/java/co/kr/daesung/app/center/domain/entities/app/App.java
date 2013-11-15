package co.kr.daesung.app.center.domain.entities.app;

import com.mysema.query.types.Predicate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/13/13
 * Time: 9:51 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Getter
@Setter
public class App {
    @Id
    private String name;
    private String description;
    @Column(name = "registerUser")
    private String registerUserId;
    private Date createTime;
    private boolean deleted;
    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "app")
    private Set<AppVersion> versions = new HashSet<>();

    @PrePersist
    private void initValues() {
        createTime = new Date();
    }
}
