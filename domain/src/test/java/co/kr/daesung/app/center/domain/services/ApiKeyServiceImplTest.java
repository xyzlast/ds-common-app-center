package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.entities.auth.AcceptProgram;
import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import co.kr.daesung.app.center.domain.entities.auth.QAcceptProgram;
import co.kr.daesung.app.center.domain.repo.auth.AcceptProgramRepository;
import com.mysema.query.types.Predicate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/11/13
 * Time: 9:57 AM
 * To change this template use File | Settings | File Templates.
 */
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DomainConfiguration.class })
public class ApiKeyServiceImplTest {
    @Autowired
    private ApiKeyService service;
    private static final String USER_ID = "201105010";
    private static final String WRONG_USER_ID = "WRONG_USER_ID";
    private ApiKey targetKey;
    private static final String TEST_PROGRAMNAME = "TEST_APPLICATION";
    private static final String TEST_APP_DESCRIPTION = "TEST_APP_DESCRIPTION";
    @Autowired
    private AcceptProgramRepository acceptProgramRepository;

    @Before
    @Rollback(false)
    public void setUp() throws Exception {
        targetKey = service.generateNewKey(USER_ID);
        assertThat(targetKey, is(not(nullValue())));
    }

    @After
    public void tearDown() throws Exception {
        service.deleteKey(USER_ID, targetKey.getId());
        ApiKey apiKey = service.getApiKey(targetKey.getId());
        assertThat(apiKey.isDeleted(), is(true));
    }

    @Test(expected = IllegalAccessException.class)
    public void testDeleteApiKeyWithException() throws Exception {
        service.deleteKey(WRONG_USER_ID, targetKey.getId());
    }

    @Test
    public void testGetApiKey() throws Exception {
        ApiKey apiKey = service.getApiKey(targetKey.getId());
        assertThat(apiKey, is(not(nullValue())));
        assertThat(apiKey.getId(), is(targetKey.getId()));
    }

    private AcceptProgram addProgramTo() throws IllegalAccessException {
        AcceptProgram program = service.addProgramTo(USER_ID, targetKey, TEST_PROGRAMNAME, TEST_APP_DESCRIPTION);
        assertThat(program, is(not(nullValue())));
        assertThat(program.getApiKey(), is(targetKey));
        return program;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddProgramTo() throws Exception {
        addProgramTo();
        addProgramTo();
    }

    @Test
    @Transactional
    public void testRemoveProgramFrom1() throws Exception {
        addProgramTo();
        boolean result = service.removeProgramFrom(USER_ID, targetKey, TEST_PROGRAMNAME);
        assertThat(result, is(true));
    }

    @Test
    public void testRemoveProgramFrom2() throws Exception {
        AcceptProgram program = addProgramTo();
        AcceptProgram anotherProgram = service.getAcceptProgram(program.getId());
        assertThat(program.getId(), is(anotherProgram.getId()));
        assertThat(program.getApiKey().getId(), is(anotherProgram.getApiKey().getId()));
        boolean result = service.removeProgramFrom(USER_ID, program.getId());
        assertThat(result, is(true));
    }

    @Test
    @Transactional(readOnly = true)
    public void testIsAcceptedKey1() throws Exception {
        addProgramTo();
        boolean isAccepted = service.isAcceptedKey(targetKey, TEST_PROGRAMNAME);
        assertThat(isAccepted, is(true));
        QAcceptProgram qAcceptProgram = QAcceptProgram.acceptProgram;
        Predicate predicate = qAcceptProgram.apiKey.eq(targetKey)
                .and(qAcceptProgram.apiKey.deleted.isFalse())
                .and(qAcceptProgram.program.eq(TEST_PROGRAMNAME))
                .and(qAcceptProgram.deleted.isFalse());

        AcceptProgram program = acceptProgramRepository.findOne(predicate);
        assertThat(program.getUsedCount(), is(1L));

        boolean isNotAccepted = service.isAcceptedKey(targetKey, "WRONG_PROGRAMNAME");
        assertThat(isNotAccepted, is(false));
    }

    @Test
    @Transactional(readOnly = true)
    public void testIsAcceptedKey2() throws Exception {
        addProgramTo();
        boolean isAccepted = service.isAcceptedKey(targetKey.getId(), TEST_PROGRAMNAME);
        assertThat(isAccepted, is(true));

        QAcceptProgram qAcceptProgram = QAcceptProgram.acceptProgram;
        Predicate predicate = qAcceptProgram.apiKey.eq(targetKey)
                .and(qAcceptProgram.apiKey.deleted.isFalse())
                .and(qAcceptProgram.program.eq(TEST_PROGRAMNAME))
                .and(qAcceptProgram.deleted.isFalse());
        AcceptProgram program = acceptProgramRepository.findOne(predicate);
        assertThat(program.getUsedCount(), is(1L));

        boolean isNotAccepted = service.isAcceptedKey(targetKey, "WRONG_PROGRAMNAME");
        assertThat(isNotAccepted, is(false));
    }

    @Test
    public void testGetTopUsedApiKeys() throws Exception {
        Page<ApiKey> apiKeys = service.getTopUsedApiKeys(0, Integer.MAX_VALUE);
        Long currentCount = Long.MAX_VALUE;
        for(ApiKey apiKey : apiKeys) {
            assertThat(apiKey.getUsedCount() <= currentCount, is(true));
            currentCount = apiKey.getUsedCount();
        }
    }

    @Test
    public void testGetTopUsedPrograms() throws Exception {
        final Page<AcceptProgram> topUsedPrograms = service.getTopUsedPrograms(0, Integer.MAX_VALUE);
        Long currentCount = Long.MAX_VALUE;
        for(AcceptProgram program : topUsedPrograms) {
            assertThat(program.getUsedCount() <= currentCount, is(true));
            currentCount = program.getUsedCount();
        }
    }
}
