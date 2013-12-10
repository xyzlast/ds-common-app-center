package co.kr.daesung.app.center.domain.services;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 12/4/13
 * Time: 9:26 PM
 * To change this template use File | Settings | File Templates.
 */
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.entities.auth.AcceptProgram;
import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import co.kr.daesung.app.center.domain.entities.auth.ApiMethod;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainConfiguration.class)
public class StatisticsServiceImplTest {
    @Autowired
    private StatisticsService service;

    @Before
    public void setUp() throws Exception {
        assertThat(service, is(not(nullValue())));
    }

    @Test
    public void sortApiMethodByUsedCount() {
        List<ApiMethod> apiMethods = service.sortApiMethodsByUsedCount();
        assertThat(apiMethods.size(), is(not(0)));
        long usedCount = Long.MAX_VALUE;
        for(ApiMethod apiMethod : apiMethods) {
            assertThat(usedCount >= apiMethod.getUsedCount(), is(true));
            usedCount = apiMethod.getUsedCount();
        }
    }

    @Test
    public void sortApiKeysByUsedCount() {
        List<ApiKey> apiKeys = service.sortApiKeysByUsedCount(100);
        assertThat(apiKeys.size(), is(not(0)));
        long usedCount = Long.MAX_VALUE;
        for(ApiKey apiKey : apiKeys) {
            assertThat(usedCount >= apiKey.getUsedCount(), is(true));
            usedCount = apiKey.getUsedCount();
        }
    }

    @Test
    public void sortProgramsByUsedCount() {
        List<AcceptProgram> programs = service.sortAcceptProgramsByUsedCount(100);
        assertThat(programs.size(), is(not(0)));
        long usedCount = Long.MAX_VALUE;
        for(AcceptProgram program : programs) {
            assertThat(usedCount >= program.getUsedCount(), is(true));
            usedCount = program.getUsedCount();
        }
    }

}
