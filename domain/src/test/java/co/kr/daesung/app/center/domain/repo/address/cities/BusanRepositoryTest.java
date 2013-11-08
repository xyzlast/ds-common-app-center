package co.kr.daesung.app.center.domain.repo.address.cities;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/5/13
 * Time: 2:00 PM
 * To change this template use File | Settings | File Templates.
 */
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.entities.address.cities.Busan;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DomainConfiguration.class })
public class BusanRepositoryTest {
    @Autowired
    private BusanRepository busanRepository;

    @Before
    public void setUp() {
        assertThat(busanRepository, is(not(nullValue())));
    }

    @Test
    public void listAll() {
        PageRequest pageRequest = new PageRequest(0, 10);
        Page<Busan> addresses = busanRepository.findAll(pageRequest);
        assertThat(addresses.getSize(), is(10));
    }
}
