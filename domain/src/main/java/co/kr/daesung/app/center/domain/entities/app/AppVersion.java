package co.kr.daesung.app.center.domain.entities.app;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/13/13
 * Time: 9:53 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Getter
@Setter
@Table(name="AppVersion")
public class AppVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "appName")
    private App app;
    @Column(name = "isTop")
    private boolean top;
    private int downloadCount;
    private String version;
    private String description;
    private String registerUser;
    private Date createTime;
    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "version")
    private List<AppFile> files = new ArrayList<>();

    @PrePersist
    public void initValues() {
        createTime = new Date();
    }
}
