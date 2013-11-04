package co.kr.daesung.app.center.domain.entities.cities;

import co.kr.daesung.app.center.domain.constants.AddressTable;
import co.kr.daesung.app.center.domain.entities.support.BaseAddress;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/4/13
 * Time: 2:40 PM
 * Gwangju entity
 */
@Entity
@Table(name = AddressTable.Gwangju)
public class Gwangju extends BaseAddress {

}
