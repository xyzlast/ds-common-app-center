package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.constants.SidoEnum;
import co.kr.daesung.app.center.domain.entities.*;
import co.kr.daesung.app.center.domain.entities.cities.*;
import co.kr.daesung.app.center.domain.entities.support.BaseAddress;
import co.kr.daesung.app.center.domain.entities.support.QBaseAddress;
import co.kr.daesung.app.center.domain.repo.EupMyeonDongRiRepository;
import co.kr.daesung.app.center.domain.repo.RoadRepository;
import co.kr.daesung.app.center.domain.repo.SiDoRepository;
import co.kr.daesung.app.center.domain.repo.SiGunGuRepository;
import co.kr.daesung.app.center.domain.repo.cities.*;
import co.kr.daesung.app.center.domain.utils.ArrayUtil;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.EntityPathBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/7/13
 * Time: 4:10 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private SiDoRepository siDoRepository;
    @Autowired
    private EupMyeonDongRiRepository eupMyeonDongRiRepository;
    @Autowired
    private RoadRepository roadRepository;
    @Autowired
    private SiGunGuRepository siGunGuRepository;
    @Autowired
    private BusanRepository busanRepository;
    @Autowired
    private GwangjuRepository gwangjuRepository;
    @Autowired
    private DaejeonRepository daejeonRepository;
    @Autowired
    private ChungcheongBukdoRepository chungcheongBukdoRepository;
    @Autowired
    private ChungcheongNamdoRepository chungcheongNamdoRepository;
    @Autowired
    private DaeguRepository daeguRepository;
    @Autowired
    private GyeonggidoRepository gyeonggidoRepository;
    @Autowired
    private GangwondoRepository gangwondoRepository;
    @Autowired
    private IncheonRepository incheonRepository;
    @Autowired
    private JejudoRepository jejudoRepository;
    @Autowired
    private JeollaBukdoRepository jeollaBukdoRepository;
    @Autowired
    private JeollaNamdoRepository jeollaNamdoRepository;
    @Autowired
    private GyeongsangBukdoRepository gyeongsangBukdoRepository;
    @Autowired
    private GyeongsangNamdoRepository gyeongsangNamdoRepository;
    @Autowired
    private SejongRepository sejongRepository;
    @Autowired
    private SeoulRepository seoulRepository;
    @Autowired
    private UlsanRepository ulsanRepository;

    @Override
    @Cacheable(value = "sidoList")
    public List<SiDo> getSiDoList() {
        Sort sort = new Sort(Sort.Direction.ASC, "sidoNumber");
        return siDoRepository.findAll(sort);
    }

    @Override
    public List<SiGunGu> getSiGunGuList(String sidoNumber) {
        QSiGunGu qSiGun = QSiGunGu.siGunGu;
        Predicate predicate = qSiGun.sido.sidoNumber.eq(sidoNumber);
        final Iterable<SiGunGu> siGunGusIterable = siGunGuRepository.findAll(predicate, qSiGun.siGunGuNumber.asc());
        return ArrayUtil.convertTo(siGunGusIterable);
    }

    @Override
    public List<EupMyeonDongRi> getEupMyeonDongRiNumbers(String haengJungDongName) {
        QEupMyeonDongRi q = QEupMyeonDongRi.eupMyeonDongRi;
        Predicate predicate = q.haengJungDongName.like("%" + haengJungDongName + "%");
        return ArrayUtil.convertTo(eupMyeonDongRiRepository.findAll(predicate));
    }

    @Override
    public List<BaseAddress> searchByJibeon(String searchText, boolean merge, int pageIndex, int pageSize) {
        Matcher matcher = extractSearchText(searchText);
        String eupMyeonDongRiName;
        Integer jibeonMainNumber = null;
        if(matcher.find() && matcher.groupCount() >= 3) {
            eupMyeonDongRiName = matcher.group(1);
            jibeonMainNumber = Integer.parseInt(matcher.group(2));
        } else {
            eupMyeonDongRiName = searchText;
        }
        final List<EupMyeonDongRi> eupMyeonDongRiList = getEupMyeonDongRiNumbers(eupMyeonDongRiName);
        List<BaseAddress> result = new ArrayList<>();
        for(EupMyeonDongRi eupMyeonDongRi : eupMyeonDongRiList) {
            List<BaseAddress> addresses =
                    searchByJibeon(eupMyeonDongRi.getSiGunGu().getSido().getSidoNumber(), eupMyeonDongRi.getId(), jibeonMainNumber, merge);
            result.addAll(addresses);
            if(result.size() >= (pageIndex + 1) * pageSize) {
                break;
            }
        }
        return result.subList(pageIndex * pageSize, Math.min(result.size() - 1, (pageIndex + 1) * pageSize));
    }

    @Override
    public List<BaseAddress> searchByJibeon(String sidoNumber, int eupMyeonDongRiNumber, Integer jibeonMainNumber, boolean merge) {
        BaseCityRepository baseCityRepository = getBaseCityRepository(sidoNumber);
        Predicate predicate = getPredicateForSearchByJibeon(sidoNumber, eupMyeonDongRiNumber, jibeonMainNumber);

        List<BaseAddress> addresses = ArrayUtil.convertTo(baseCityRepository.findAll(predicate));
        if(merge) {
            List<BaseAddress> mergedOutput = new ArrayList<>();
            for(BaseAddress baseAddress : addresses) {
                boolean findDuplicatedItem = false;
                for(BaseAddress mergedAddress : mergedOutput) {
                    if(mergedAddress.getJibeonMainNumber().equals(baseAddress.getJibeonMainNumber()) &&
                            mergedAddress.getBuildingName().equals(baseAddress.getBuildingName())) {
                        findDuplicatedItem = true;
                        break;
                    }
                }
                if(!findDuplicatedItem) {
                    mergedOutput.add(baseAddress);
                }
            }
            return mergedOutput;
        } else {
            return addresses;
        }
    }

    private Predicate getPredicateForSearchByJibeon(String sidoNumber, int eupMyeonDongRiNumber, Integer jibeonMainNumber) {
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

    private List<BaseAddress> findAddressesByRoad(String sidoNumber, List<Road> roads) {
        BaseCityRepository cityRepository = getBaseCityRepository(sidoNumber);
        QBaseAddress qBaseAddress = QBaseAddress.baseAddress;
        return ArrayUtil.convertTo(cityRepository.findAll(qBaseAddress.road.in(roads)));
    }

    private BaseCityRepository getBaseCityRepository(String sidoNumber) {
        SidoEnum sidoEnum = SidoEnum.getEnum(sidoNumber);
        switch(sidoEnum) {
            case SEOUL:
                return seoulRepository;
            case BUSAN:
                return busanRepository;
            case DAEGU:
                return daeguRepository;
            case INCHEON:
                return incheonRepository;
            case GWANGJU:
                return gwangjuRepository;
            case DAEJEON:
                return daejeonRepository;
            case ULSAN:
                return ulsanRepository;
            case SEJONG:
                return sejongRepository;
            case GYEONGGIDO:
                return gyeonggidoRepository;
            case GANGWONDO:
                return gangwondoRepository;
            case CHUNGCHEONGBUKDO:
                return chungcheongBukdoRepository;
            case CHUNGCHEONGNAMDO:
                return chungcheongNamdoRepository;
            case JEOLLABUKDO:
                return jeollaBukdoRepository;
            case JEOLLONAMDO:
                return jeollaNamdoRepository;
            case GYEONGSANGBUKDO:
                return gyeongsangBukdoRepository;
            case GYEONGSANGNAMDO:
                return gyeongsangNamdoRepository;
            case JEJUDO:
                return jejudoRepository;
            default:
                return null;
        }
    }

    private Matcher extractSearchText(String searchText) {
        String regex = "([^-\\s]*)\\s(\\d*)[^\\d]*";
        Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(searchText);
        return matcher;
    }

    @Override
    public List<Road> getRoadNumbers(String searchText) {
        QRoad qRoad = QRoad.road;
        Predicate predicate = qRoad.roadName.like("%" + searchText + "%");
        return ArrayUtil.convertTo(roadRepository.findAll(predicate));
    }

    @Override
    public List<BaseAddress> searchByRoad(String sidoNumber, String searchText, boolean merge, int pageIndex, int pageSize) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<BaseAddress> searchAddressBySiDoSiGunGuRoadList(String sidoNumber, String sigunguNumber, String searchText, int pageIndex, int pageSize) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<BaseAddress> searchByBuilding(String buildingName, int pageIndex, int pageSize) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<BaseAddress> searchByBuilding(String sidoNumber, String buildingName, int pageIndex, int pageSize) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean insertAddressByFileData(String fileFullName, int encodingCode) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
