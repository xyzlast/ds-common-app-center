package co.kr.daesung.app.center.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * User: ykyoon
 * Date: 11/4/13
 * Time: 11:02 AM
 * Si, Gun, Gu entity
 */
@Entity
@Getter
@Setter
public class SiGunGu {
    @Id
    private String sigunguNumber;
    @ManyToOne
    private SiDo sido;
    private String sigungoName;
}
