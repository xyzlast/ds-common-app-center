package co.kr.daesung.app.center.domain.repo.address;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.entities.address.QSiDo;
import co.kr.daesung.app.center.domain.entities.address.SiDo;
import co.kr.daesung.app.center.domain.repo.address.SiDoRepository;
import com.mysema.query.types.Predicate;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =  {DomainConfiguration.class})
public class SiDoRepositoryTest {
    @Autowired
    private SiDoRepository siDoRepository;

    @Test
    public void getSiDoList() {
        List<SiDo> sidoList = siDoRepository.findAll();
        for(SiDo sido : sidoList) {
            System.out.println(sido.getSidoName());
        }
    }

    @Test
    public void findSidoName() {
        QSiDo qSiDo = QSiDo.siDo;
        Predicate predicate = qSiDo.sidoName.like("서%");
        Iterable<SiDo> sidos = siDoRepository.findAll(predicate);

        int count = 0;
        Iterator iter = sidos.iterator();
        while(iter.hasNext()) {
            SiDo sido = (SiDo) iter.next();
            count++;
        }
        assertThat(count, is(1));
    }
}
