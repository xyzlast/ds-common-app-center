package co.kr.daesung.app.center.domain.entities.address.support;

import co.kr.daesung.app.center.domain.entities.address.EupMyeonDongRi;
import co.kr.daesung.app.center.domain.entities.address.Road;
import co.kr.daesung.app.center.domain.entities.address.SiGunGu;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/4/13
 * Time: 11:03 AM
 * Address mapped Super class
 */
@MappedSuperclass
@Getter
@Setter
@ToString
public class BaseAddress {
    @Id
    @Column(length = 25)
    private String buildingControlNumber;
    @ManyToOne
    @JoinColumn(name = "SiGunGu")
    private SiGunGu siGunGu;
    @ManyToOne
    @JoinColumn(name = "road")
    private Road road;
    @ManyToOne
    @JoinColumn(name = "EupMyeonDongRi")
    private EupMyeonDongRi eupMyeonDongRi;

    private String postCode;
    private Integer jibeonMainNumber;
    private Integer jibeonSubNumber;
    private Integer buildingMainNumber;
    private Integer buildingSubNumber;
    private String buildingName;
    private String buildingDetailName;
    private String siGunguBuildingName;
}
