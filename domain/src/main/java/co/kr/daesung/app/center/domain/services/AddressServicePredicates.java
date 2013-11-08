package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.constants.SidoEnum;
import co.kr.daesung.app.center.domain.entities.address.Road;
import co.kr.daesung.app.center.domain.entities.address.cities.*;
import com.mysema.query.types.Predicate;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/8/13
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddressServicePredicates {
    public static Predicate getPredicateForSearchByJibeon(String sidoNumber, int eupMyeonDongRiNumber, Integer jibeonMainNumber) {
        Predicate predicate;
        SidoEnum sidoEnum = SidoEnum.getEnum(sidoNumber);
        switch(sidoEnum) {
            case SEOUL:
                if(jibeonMainNumber != null) {
                    predicate = QSeoul.seoul.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber)
                            .and(QSeoul.seoul.jibeonMainNumber.eq(jibeonMainNumber));
                } else {
                    predicate = QSeoul.seoul.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber);
                }
                break;
            case BUSAN:
                if(jibeonMainNumber != null) {
                    predicate = QBusan.busan.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber)
                            .and(QBusan.busan.jibeonMainNumber.eq(jibeonMainNumber));
                } else {
                    predicate = QBusan.busan.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber);
                }
                break;
            case DAEGU:
                if(jibeonMainNumber != null) {
                    predicate = QDaegu.daegu.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber)
                            .and(QDaegu.daegu.jibeonMainNumber.eq(jibeonMainNumber));
                } else {
                    predicate = QDaegu.daegu.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber);
                }
                break;
            case INCHEON:
                if(jibeonMainNumber != null) {
                    predicate = QIncheon.incheon.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber)
                            .and(QIncheon.incheon.jibeonMainNumber.eq(jibeonMainNumber));
                } else {
                    predicate = QIncheon.incheon.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber);
                }
                break;
            case GWANGJU:
                if(jibeonMainNumber != null) {
                    predicate = QGwangju.gwangju.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber)
                            .and(QGwangju.gwangju.jibeonMainNumber.eq(jibeonMainNumber));
                } else {
                    predicate = QGwangju.gwangju.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber);
                }
                break;
            case DAEJEON:
                if(jibeonMainNumber != null) {
                    predicate = QDaejeon.daejeon.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber)
                            .and(QDaejeon.daejeon.jibeonMainNumber.eq(jibeonMainNumber));
                } else {
                    predicate = QDaejeon.daejeon.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber);
                }
                break;
            case ULSAN:
                if(jibeonMainNumber != null) {
                    predicate = QUlsan.ulsan.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber)
                            .and(QUlsan.ulsan.jibeonMainNumber.eq(jibeonMainNumber));
                } else {
                    predicate = QUlsan.ulsan.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber);
                }
                break;
            case SEJONG:
                if(jibeonMainNumber != null) {
                    predicate = QSejong.sejong.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber)
                            .and(QSejong.sejong.jibeonMainNumber.eq(jibeonMainNumber));
                } else {
                    predicate = QSejong.sejong.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber);
                }
                break;
            case GYEONGGIDO:
                if(jibeonMainNumber != null) {
                    predicate = QGyeonggido.gyeonggido.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber)
                            .and(QGyeonggido.gyeonggido.jibeonMainNumber.eq(jibeonMainNumber));
                } else {
                    predicate = QGyeonggido.gyeonggido.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber);
                }
                break;
            case GANGWONDO:
                if(jibeonMainNumber != null) {
                    predicate = QGangwondo.gangwondo.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber)
                            .and(QGangwondo.gangwondo.jibeonMainNumber.eq(jibeonMainNumber));
                } else {
                    predicate = QGangwondo.gangwondo.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber);
                }
                break;
            case CHUNGCHEONGBUKDO:
                if(jibeonMainNumber != null) {
                    predicate = QChungcheongBukdo.chungcheongBukdo.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber)
                            .and(QChungcheongBukdo.chungcheongBukdo.jibeonMainNumber.eq(jibeonMainNumber));
                } else {
                    predicate = QChungcheongBukdo.chungcheongBukdo.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber);
                }
                break;
            case CHUNGCHEONGNAMDO:
                if(jibeonMainNumber != null) {
                    predicate = QChungcheongNamdo.chungcheongNamdo.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber)
                            .and(QChungcheongNamdo.chungcheongNamdo.jibeonMainNumber.eq(jibeonMainNumber));
                } else {
                    predicate = QChungcheongNamdo.chungcheongNamdo.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber);
                }
                break;
            case JEOLLABUKDO:
                if(jibeonMainNumber != null) {
                    predicate = QJeollaBukdo.jeollaBukdo.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber)
                            .and(QJeollaBukdo.jeollaBukdo.jibeonMainNumber.eq(jibeonMainNumber));
                } else {
                    predicate = QJeollaBukdo.jeollaBukdo.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber);
                }
                break;
            case JEOLLONAMDO:
                if(jibeonMainNumber != null) {
                    predicate = QJeollaNamdo.jeollaNamdo.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber)
                            .and(QJeollaNamdo.jeollaNamdo.jibeonMainNumber.eq(jibeonMainNumber));
                } else {
                    predicate = QJeollaNamdo.jeollaNamdo.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber);
                }

                break;
            case GYEONGSANGBUKDO:
                if(jibeonMainNumber != null) {
                    predicate = QGyeongsangBukdo.gyeongsangBukdo.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber)
                            .and(QGyeongsangBukdo.gyeongsangBukdo.jibeonMainNumber.eq(jibeonMainNumber));
                } else {
                    predicate = QGyeongsangBukdo.gyeongsangBukdo.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber);
                }
                break;
            case GYEONGSANGNAMDO:
                if(jibeonMainNumber != null) {
                    predicate = QGyeongsangNamdo.gyeongsangNamdo.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber)
                            .and(QGyeongsangNamdo.gyeongsangNamdo.jibeonMainNumber.eq(jibeonMainNumber));
                } else {
                    predicate = QGyeongsangNamdo.gyeongsangNamdo.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber);
                }
                break;
            case JEJUDO:
            default:
                if(jibeonMainNumber != null) {
                    predicate = QJejudo.jejudo.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber)
                            .and(QJejudo.jejudo.jibeonMainNumber.eq(jibeonMainNumber));
                } else {
                    predicate = QJejudo.jejudo.eupMyeonDongRi.id.eq(eupMyeonDongRiNumber);
                }
                break;
        }
        return predicate;
    }

    public static Predicate getPredicateForSearchByBuilding(String sidoNumber, String buildingName) {
        SidoEnum sido = SidoEnum.getEnum(sidoNumber);
        Predicate predicate = null;
        String likeQuery = "%" + buildingName + "%";
        switch(sido) {
            case SEOUL:
                predicate = QSeoul.seoul.buildingName.like(likeQuery);
                break;
            case BUSAN:
                predicate = QBusan.busan.buildingName.like(likeQuery);
                break;
            case DAEGU:
                predicate = QDaegu.daegu.buildingName.like(likeQuery);
                break;
            case INCHEON:
                predicate = QIncheon.incheon.buildingName.like(likeQuery);
                break;
            case GWANGJU:
                predicate = QGwangju.gwangju.buildingName.like(likeQuery);
                break;
            case DAEJEON:
                predicate = QDaejeon.daejeon.buildingName.like(likeQuery);
                break;
            case ULSAN:
                predicate = QUlsan.ulsan.buildingName.like(likeQuery);
                break;
            case SEJONG:
                predicate = QSejong.sejong.buildingName.like(likeQuery);
                break;
            case GYEONGGIDO:
                predicate = QGyeonggido.gyeonggido.buildingName.like(likeQuery);
                break;
            case GANGWONDO:
                predicate = QGangwondo.gangwondo.buildingName.like(likeQuery);
                break;
            case CHUNGCHEONGBUKDO:
                predicate = QChungcheongBukdo.chungcheongBukdo.buildingName.like(likeQuery);
                break;
            case CHUNGCHEONGNAMDO:
                predicate = QChungcheongNamdo.chungcheongNamdo.buildingName.like(likeQuery);
                break;
            case JEOLLABUKDO:
                predicate = QJeollaBukdo.jeollaBukdo.buildingName.like(likeQuery);
                break;
            case JEOLLONAMDO:
                predicate = QJeollaNamdo.jeollaNamdo.buildingName.like(likeQuery);
                break;
            case GYEONGSANGBUKDO:
                predicate = QGyeongsangBukdo.gyeongsangBukdo.buildingName.like(likeQuery);
                break;
            case GYEONGSANGNAMDO:
                predicate = QGyeongsangNamdo.gyeongsangNamdo.buildingName.like(likeQuery);
                break;
            case JEJUDO:
            default:
                predicate = QJejudo.jejudo.buildingName.like(likeQuery);
                break;
        }
        return predicate;
    }

    public static Predicate getPredicateForSearchByRoad(SidoEnum sido, List<Road> roads, Integer buildingMainNumber) {
        Predicate predicate;
        switch(sido) {
            case SEOUL:
                if(buildingMainNumber != null) {
                    predicate = QSeoul.seoul.road.in(roads)
                            .and(QSeoul.seoul.buildingMainNumber.eq(buildingMainNumber));
                } else {
                    predicate = QSeoul.seoul.road.in(roads);
                }
                break;
            case BUSAN:
                if(buildingMainNumber != null) {
                    predicate = QBusan.busan.road.in(roads)
                            .and(QBusan.busan.buildingMainNumber.eq(buildingMainNumber));
                } else {
                    predicate = QBusan.busan.road.in(roads);
                }
                break;
            case DAEGU:
                if(buildingMainNumber != null) {
                    predicate = QDaegu.daegu.road.in(roads)
                            .and(QDaegu.daegu.buildingMainNumber.eq(buildingMainNumber));
                } else {
                    predicate = QDaegu.daegu.road.in(roads);
                }
                break;
            case INCHEON:
                if(buildingMainNumber != null) {
                    predicate = QIncheon.incheon.road.in(roads)
                            .and(QIncheon.incheon.buildingMainNumber.eq(buildingMainNumber));
                } else {
                    predicate = QIncheon.incheon.road.in(roads);
                }
                break;
            case GWANGJU:
                if(buildingMainNumber != null) {
                    predicate = QGwangju.gwangju.road.in(roads)
                            .and(QGwangju.gwangju.buildingMainNumber.eq(buildingMainNumber));
                } else {
                    predicate = QGwangju.gwangju.road.in(roads);
                }
                break;
            case DAEJEON:
                if(buildingMainNumber != null) {
                    predicate = QDaejeon.daejeon.road.in(roads)
                            .and(QDaejeon.daejeon.buildingMainNumber.eq(buildingMainNumber));
                } else {
                    predicate = QDaejeon.daejeon.road.in(roads);
                }
                break;
            case ULSAN:
                if(buildingMainNumber != null) {
                    predicate = QUlsan.ulsan.road.in(roads)
                            .and(QUlsan.ulsan.buildingMainNumber.eq(buildingMainNumber));
                } else {
                    predicate = QUlsan.ulsan.road.in(roads);
                }
                break;
            case SEJONG:
                if(buildingMainNumber != null) {
                    predicate = QSejong.sejong.road.in(roads)
                            .and(QSejong.sejong.buildingMainNumber.eq(buildingMainNumber));
                } else {
                    predicate = QSejong.sejong.road.in(roads);
                }
                break;
            case GYEONGGIDO:
                if(buildingMainNumber != null) {
                    predicate = QGyeonggido.gyeonggido.road.in(roads)
                            .and(QGyeonggido.gyeonggido.buildingMainNumber.eq(buildingMainNumber));
                } else {
                    predicate = QGyeonggido.gyeonggido.road.in(roads);
                }
                break;
            case GANGWONDO:
                if(buildingMainNumber != null) {
                    predicate = QGangwondo.gangwondo.road.in(roads)
                            .and(QGangwondo.gangwondo.buildingMainNumber.eq(buildingMainNumber));
                } else {
                    predicate = QGangwondo.gangwondo.road.in(roads);
                }
                break;
            case CHUNGCHEONGBUKDO:
                if(buildingMainNumber != null) {
                    predicate = QChungcheongBukdo.chungcheongBukdo.road.in(roads)
                            .and(QChungcheongBukdo.chungcheongBukdo.buildingMainNumber.eq(buildingMainNumber));
                } else {
                    predicate = QChungcheongBukdo.chungcheongBukdo.road.in(roads);
                }
                break;
            case CHUNGCHEONGNAMDO:
                if(buildingMainNumber != null) {
                    predicate = QChungcheongNamdo.chungcheongNamdo.road.in(roads)
                            .and(QChungcheongNamdo.chungcheongNamdo.buildingMainNumber.eq(buildingMainNumber));
                } else {
                    predicate = QChungcheongNamdo.chungcheongNamdo.road.in(roads);
                }
                break;
            case JEOLLABUKDO:
                if(buildingMainNumber != null) {
                    predicate = QJeollaBukdo.jeollaBukdo.road.in(roads)
                            .and(QJeollaBukdo.jeollaBukdo.buildingMainNumber.eq(buildingMainNumber));
                } else {
                    predicate = QJeollaBukdo.jeollaBukdo.road.in(roads);
                }
                break;
            case JEOLLONAMDO:
                if(buildingMainNumber != null) {
                    predicate = QJeollaNamdo.jeollaNamdo.road.in(roads)
                            .and(QJeollaNamdo.jeollaNamdo.buildingMainNumber.eq(buildingMainNumber));
                } else {
                    predicate = QJeollaNamdo.jeollaNamdo.road.in(roads);
                }
                break;
            case GYEONGSANGBUKDO:
                if(buildingMainNumber != null) {
                    predicate = QGyeongsangBukdo.gyeongsangBukdo.road.in(roads)
                            .and(QGyeongsangBukdo.gyeongsangBukdo.buildingMainNumber.eq(buildingMainNumber));
                } else {
                    predicate = QGyeongsangBukdo.gyeongsangBukdo.road.in(roads);
                }
                break;
            case GYEONGSANGNAMDO:
                if(buildingMainNumber != null) {
                    predicate = QGyeongsangNamdo.gyeongsangNamdo.road.in(roads)
                            .and(QGyeongsangNamdo.gyeongsangNamdo.buildingMainNumber.eq(buildingMainNumber));
                } else {
                    predicate = QGyeongsangNamdo.gyeongsangNamdo.road.in(roads);
                }
                break;
            case JEJUDO:
            default:
                if(buildingMainNumber != null) {
                    predicate = QJejudo.jejudo.road.in(roads)
                            .and(QJejudo.jejudo.buildingMainNumber.eq(buildingMainNumber));
                } else {
                    predicate = QJejudo.jejudo.road.in(roads);
                }
                break;
        }
        return predicate;
    }
}
