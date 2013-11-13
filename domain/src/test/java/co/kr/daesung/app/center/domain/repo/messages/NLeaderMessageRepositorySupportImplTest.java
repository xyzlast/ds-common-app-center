package co.kr.daesung.app.center.domain.repo.messages;

import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/12/13
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DomainConfiguration.class })
@Transactional
public class NLeaderMessageRepositorySupportImplTest {
    @Autowired
    private NLeaderMessageRepositorySupport nLeaderMessageRepositorySupport;

    @Before
    public void setUp() {
        assertThat(nLeaderMessageRepositorySupport, is(not(nullValue())));
    }

    @Test
    public void testMakeSentToAll() throws Exception {
        Long result = nLeaderMessageRepositorySupport.makeSentToAll();
        assertThat(result >= 0, is(true));
    }
}
