package co.kr.daesung.app.center.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * User: ykyoon
 * Date: 11/4/13
 * Time: 11:01 AM
 * Road address entity
 */

@Entity
@Getter
@Setter
public class Road {
    @Id
    private String roadNumber;
    private String roadName;
}
