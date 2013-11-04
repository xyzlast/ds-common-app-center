package co.kr.daesung.app.center.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * User: ykyoon
 * Date: 11/4/13
 * Time: 10:58 AM
 * SiDo entity class
 */
@Entity
@Getter
@Setter
public class SiDo {
    @Id
    private String sidoNumber;
    private String sidoName;
    private String sidoTableName;
}
