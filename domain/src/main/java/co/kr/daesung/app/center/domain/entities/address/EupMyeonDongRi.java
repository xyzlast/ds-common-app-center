package co.kr.daesung.app.center.domain.entities.address;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/4/13
 * Time: 11:06 AM
 * Eup, Myeon, Dong, Ri entity
 */
@Entity
@Getter
@Setter
public class EupMyeonDongRi {
    @Id
    @Column(name = "EupMyeonDongRiNumber")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "siGunGu")
    private SiGunGu siGunGu;

    @Column(length = 20, nullable = true)
    private String beopJungEupMyeonDongName;
    @Column(length = 20, nullable = true)
    private String beopJungRiName;
    @Column(length = 20, nullable = true)
    private String haengJungDongName;
}
