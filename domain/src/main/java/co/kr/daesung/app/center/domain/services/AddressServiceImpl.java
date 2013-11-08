package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.constants.SidoEnum;
import co.kr.daesung.app.center.domain.entities.address.*;
import co.kr.daesung.app.center.domain.entities.address.EupMyeonDongRi;
import co.kr.daesung.app.center.domain.entities.address.Road;
import co.kr.daesung.app.center.domain.entities.address.SiDo;
import co.kr.daesung.app.center.domain.entities.address.SiGunGu;
import co.kr.daesung.app.center.domain.entities.address.support.BaseAddress;
import co.kr.daesung.app.center.domain.entities.address.support.QBaseAddress;
import co.kr.daesung.app.center.domain.repo.address.EupMyeonDongRiRepository;
import co.kr.daesung.app.center.domain.repo.address.RoadRepository;
import co.kr.daesung.app.center.domain.repo.address.SiDoRepository;
import co.kr.daesung.app.center.domain.repo.address.SiGunGuRepository;
import co.kr.daesung.app.center.domain.repo.address.cities.*;
import co.kr.daesung.app.center.domain.utils.ArrayUtil;
import co.kr.daesung.app.center.domain.utils.RegexResultForSearchText;
import co.kr.daesung.app.center.domain.utils.SearchTextRegex;
import com.mysema.query.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        final RegexResultForSearchText searchTextItem = SearchTextRegex.getSearchTextItem(searchText);
        String enupMyeonDongRiName = searchTextItem.getMainText();
        Integer jibeonMainNumber = searchTextItem.getMainNumber();

        final List<EupMyeonDongRi> eupMyeonDongRiList = getEupMyeonDongRiNumbers(enupMyeonDongRiName);
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
        Predicate predicate = AddressServicePredicates.getPredicateForSearchByJibeon(sidoNumber, eupMyeonDongRiNumber, jibeonMainNumber);

        List<BaseAddress> addresses = ArrayUtil.convertTo(baseCityRepository.findAll(predicate));
        if(merge) {
            return mergeAddresses(addresses);
        } else {
            return addresses;
        }
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

    @Override
    public List<Road> getRoadNumbers(String searchText) {
        QRoad qRoad = QRoad.road;
        Predicate predicate = qRoad.roadName.like("%" + searchText + "%");
        return ArrayUtil.convertTo(roadRepository.findAll(predicate));
    }

    @Override
    public List<BaseAddress> searchByRoad(String sidoNumber, String searchText, boolean merge, int pageIndex, int pageSize) {
        RegexResultForSearchText regexResult = SearchTextRegex.getSearchTextItem(searchText);
        String roadName = regexResult.getMainText();
        Integer buildingMainNumber = regexResult.getMainNumber();

        BaseCityRepository repository = getBaseCityRepository(sidoNumber);
        Predicate roadPredicate = QRoad.road.roadName.like("%" + roadName + "%");
        final List<Road> roads = ArrayUtil.convertTo(roadRepository.findAll(roadPredicate));

        final Predicate predicateForSearchByRoad =
                AddressServicePredicates.getPredicateForSearchByRoad(SidoEnum.getEnum(sidoNumber), roads, buildingMainNumber);
        PageRequest pageRequest = new PageRequest(pageIndex, pageSize);
        return ArrayUtil.convertTo(repository.findAll(predicateForSearchByRoad, pageRequest));
    }

    @Override
    public List<BaseAddress> searchByBuilding(String buildingName) {
        List<BaseAddress> addresses = new ArrayList<>();
        for(SidoEnum sido : SidoEnum.values()) {
            String sidoNumber = Integer.valueOf(sido.getValue()).toString();
            addresses.addAll(searchByBuilding(sidoNumber, buildingName, 0, Integer.MAX_VALUE));
        }
        return addresses;
    }

    @Override
    public List<BaseAddress> searchByBuilding(String sidoNumber, String buildingName, int pageIndex, int pageSize) {
        BaseCityRepository repository = getBaseCityRepository(sidoNumber);
        PageRequest pageRequest = new PageRequest(pageIndex, pageSize);
        Predicate predicate = AddressServicePredicates.getPredicateForSearchByBuilding(sidoNumber, buildingName);
        return ArrayUtil.convertTo(repository.findAll(predicate, pageRequest));
    }

    @Override
    public boolean insertAddressByFileData(String fileFullName, int encodingCode) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private List<BaseAddress> mergeAddresses(List<BaseAddress> inputAddresses) {
        List<BaseAddress> outputAddresses = new ArrayList<>();
        for(BaseAddress inputAddress : inputAddresses) {
            boolean duplicated = false;
            for(BaseAddress outputAddress : outputAddresses) {
                if(outputAddress.getBuildingName().equals(inputAddress.getBuildingName()) &&
                        outputAddress.getJibeonMainNumber().equals(inputAddress.getJibeonMainNumber())) {
                    duplicated = true;
                    break;
                }
            }
            if(!duplicated) {
                outputAddresses.add(inputAddress);
            }
        }
        return outputAddresses;
    }
}
