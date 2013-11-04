package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.SiDo;
import co.kr.daesung.app.center.domain.entities.SiGunGu;

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
}
