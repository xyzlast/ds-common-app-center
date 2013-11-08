package co.kr.daesung.app.center.domain.entities.address.cities;

import co.kr.daesung.app.center.domain.constants.AddressTable;
import co.kr.daesung.app.center.domain.entities.address.support.BaseAddress;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/4/13
 * Time: 4:30 PM
 * Incheon entity
 */
@Entity
@Table(name = AddressTable.Incheon)
public class Incheon extends BaseAddress {
}
