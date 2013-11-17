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

    private enum JibeonAddressType {
        NO_NUMBER,
        MAIN_ONLY,
        ALL
    }

    private JibeonAddressType getJibeonAddressType() {
        if(jibeonMainNumber == null && jibeonSubNumber == null) {
            return JibeonAddressType.NO_NUMBER;
        } else if(jibeonMainNumber != null && jibeonSubNumber == null) {
            return JibeonAddressType.MAIN_ONLY;
        } else {
            return JibeonAddressType.ALL;
        }
    }

    public String toJibeonAddress(boolean merge) {
        JibeonAddressType addressType = getJibeonAddressType();
        if(addressType == JibeonAddressType.ALL && !merge) {
            return String.format("%s %s %s %d-%d %s",
                    siGunGu.getSido().getSidoName(),
                    siGunGu.getSiGunGuName(),
                    eupMyeonDongRi.getHaengJungDongName(),
                    jibeonMainNumber,
                    jibeonSubNumber,
                    buildingName);
        } else if( (addressType == JibeonAddressType.ALL && merge) ||
                addressType == JibeonAddressType.MAIN_ONLY) {
            return String.format("%s %s %s %d %s",
                    siGunGu.getSido().getSidoName(),
                    siGunGu.getSiGunGuName(),
                    eupMyeonDongRi.getHaengJungDongName(),
                    jibeonMainNumber,
                    buildingName);
        } else {
            return String.format("%s %s %s %s",
                    siGunGu.getSido().getSidoName(),
                    siGunGu.getSiGunGuName(),
                    eupMyeonDongRi.getHaengJungDongName(),
                    buildingName);
        }
    }

    public String toRoadAddress() {
        if(buildingMainNumber != null) {
            return String.format("%s %s %s %d %s",
                    siGunGu.getSido().getSidoName(),
                    siGunGu.getSiGunGuName(),
                    road.getRoadName(),
                    buildingMainNumber,
                    buildingName);
        } else {
            return String.format("%s %s %s %s",
                    siGunGu.getSido().getSidoName(),
                    siGunGu.getSiGunGuName(),
                    road.getRoadName(),
                    buildingName);
        }
    }

    public String getDisplayPostCode() {
        if(this.postCode.equals("")) {
            return "";
        } else {
            String firstCode = this.postCode.substring(0, 3);
            String secondCode = this.postCode.substring(3);
            return String.format("%s-%s", firstCode, secondCode);
        }
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s",
                getDisplayPostCode(), toJibeonAddress(false), toRoadAddress());
    }
}
