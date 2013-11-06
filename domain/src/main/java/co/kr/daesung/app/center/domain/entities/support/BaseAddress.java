package co.kr.daesung.app.center.domain.entities.support;

import co.kr.daesung.app.center.domain.entities.EupMyeonDongRi;
import co.kr.daesung.app.center.domain.entities.Road;
import co.kr.daesung.app.center.domain.entities.SiGunGu;
import lombok.Getter;
import lombok.Setter;

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
public class BaseAddress {
    @Id
    @Column(length = 25)
    private String buildingControlNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SiGunGu")
    private SiGunGu siGunGu;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "road")
    private Road road;
    @ManyToOne(fetch = FetchType.LAZY)
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
