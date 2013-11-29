package co.kr.daesung.app.center.domain.entities.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/11/13
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "Notice")
@Getter
@Setter
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "isTop")
    private boolean top;
    private String title;
    @Column(columnDefinition = "text")
    private String content;
    private Date createTime;
    @Column(name = "WriterId")
    private String writer;
    private boolean deleted;

    @PrePersist
    public void initValues() {
        createTime = new Date();
    }
}
