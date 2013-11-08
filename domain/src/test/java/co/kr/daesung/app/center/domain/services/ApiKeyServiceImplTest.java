package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/9/13
 * Time: 2:14 AM
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

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DomainConfiguration.class })
public class ApiKeyServiceImplTest {

    @Autowired
    private ApiKeyService service;
    private static final String USER_ID = "201105010";
    private ApiKey targetKey;

    @Before
    public void setUp() {
        targetKey = service.generateNewKey(USER_ID);
        assertThat(targetKey, is(not(nullValue())));
        assertThat(targetKey.getId(), is(not(nullValue())));
    }

    @After
    public void tearDown() {
        if(targetKey != null) {
            service.deleteKey(targetKey.getId());
        }
    }


    @Test
    public void testGetApiKeys() throws Exception {

    }

    @Test
    public void testGetApiKey() throws Exception {

    }

    @Test
    public void testAddProgramTo() throws Exception {

    }

    @Test
    public void testRemoveProgramFrom1() throws Exception {

    }

    @Test
    public void testRemoveProgramFrom2() throws Exception {

    }

    @Test
    public void testIsAcceptedKey() throws Exception {

    }

    @Test
    public void testGetTopUsedApiKeys() throws Exception {

    }

    @Test
    public void testGetTopUsedPrograms() throws Exception {

    }
}
