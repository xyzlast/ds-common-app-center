package co.kr.daesung.app.center.domain.entities.auth;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Getter
@Setter
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 16)
    private String username;
    @Column(length = 255)
    private String password;
    @Column(length = 16)
    private String name;
    @Column(length = 255)
    private String rolesString;
    private boolean deleted;

    public String[] getRoles() {
        if(rolesString == null) {
            return new String[] {};
        } else {
            return rolesString.split(";");
        }
    }
}
