package co.kr.daesung.app.center.domain.entities.address.cities;

import co.kr.daesung.app.center.domain.constants.AddressTable;
import co.kr.daesung.app.center.domain.entities.address.support.BaseAddress;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/4/13
 * Time: 2:41 PM
 * Gyeongido entity
 */
@Entity
@Table(name = AddressTable.Gyeonggido)
public class Gyeonggido extends BaseAddress {

}
