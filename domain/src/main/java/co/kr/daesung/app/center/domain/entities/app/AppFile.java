package co.kr.daesung.app.center.domain.entities.app;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/13/13
 * Time: 10:12 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Getter
@Setter
public class AppFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "versionId")
    private AppVersion version;
    private String fileName;
    private String localFileName;
    private String contentType;
}
