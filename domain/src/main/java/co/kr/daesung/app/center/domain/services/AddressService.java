package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.address.EupMyeonDongRi;
import co.kr.daesung.app.center.domain.entities.address.Road;
import co.kr.daesung.app.center.domain.entities.address.SiDo;
import co.kr.daesung.app.center.domain.entities.address.SiGunGu;
import co.kr.daesung.app.center.domain.entities.address.support.BaseAddress;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/4/13
 * Time: 4:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AddressService {
    List<SiDo> getSiDoList();
    List<SiGunGu> getSiGunGuList(String sidoNumber);

    // 지번검색(읍/면/동/리)에 대한 읍/면/동/리 번호들
    List<EupMyeonDongRi> getEupMyeonDongRiNumbers(String haengJungDongName);

    // 지번검색(읍/면/동/리)에 대한 리스트
    List<BaseAddress> searchByJibeon(String searchText, boolean merge, int pageIndex, int pageSize);
    List<BaseAddress> searchByJibeon(String sidoNumber, int eupMyeonDongRiNumber, Integer jibeonMainNUmber, boolean merge);

    // 도로명검색(도로명)에 대한 도로명 번호들
    List<Road> getRoadNumbers(String searchText);

    // 도로명검색(도로명)에 대한 리스트
    List<BaseAddress> searchByRoad(String sidoNumber, String searchText, boolean merge, int pageIndex, int pageSize);

    // 시도, 시군구, 검색된 건물명에 대한 주소(추가)
    List<BaseAddress> searchByBuilding(String buildingName);
    List<BaseAddress> searchByBuilding(String sidoNumber, String buildingName, int pageIndex, int pageSize);


    boolean clearAllAddresses();
    // 행정안전부에서 매월 제공하는 대표지번 파일을 이용하여 데이터베이스에 Insert, 시/도, 읍면동, 길
    boolean insertBaseDataFromFile(String fileFullName, String encoding);
    // 행정안전부에서 매월 제공하는 대표지번 파일을 이용하여 데이터베이스에 Insert, 각 Address. 반드시 insertBaseDataFromFile 후, 처리되어야지 됨 - Transaction 문제로 인하여 두개로 처리
    boolean insertAddressFromFile(String fileFullName, String encoding);
}
