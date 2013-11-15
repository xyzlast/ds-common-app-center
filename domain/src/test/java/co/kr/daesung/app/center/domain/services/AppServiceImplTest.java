package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.entities.app.App;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/13/13
 * Time: 10:33 AM
 * To change this template use File | Settings | File Templates.
 */
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
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
public class AppServiceImplTest {
    @Autowired
    private AppService appService;
    private App app;

    private static final String APP_NAME = "TEST_APP";
    private static final String APP_DESCRIPTION = "TEST_APP_DES";
    private static final String USER_ID = "201105010";

    @Before
    public void setUp() {
        app = appService.createNewApp(APP_NAME, APP_DESCRIPTION, USER_ID);
        assertThat(app, is(not(nullValue())));
    }

    @Test
    @Transactional
    @Rollback(false)
    public void deleteApp() {
        appService.deleteApp(APP_NAME);
        App app = appService.getApp(APP_NAME);
        assertThat(app.isDeleted(), is(true));
    }

    @Test
    public void testAddNewAppVersion() throws Exception {

    }

    @Test
    public void testEditAppVersion() throws Exception {

    }

    @Test
    public void testSetTopVersion() throws Exception {

    }

    @Test
    public void testGetApp() throws Exception {
        App app = appService.getApp(APP_NAME);
        assertThat(app, is(not(nullValue())));
        deleteApp();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAppException() throws Exception {
        App app = appService.getApp("WRONG_APP_NAME");
        deleteApp();
    }

    @Test
    public void testGetApps() throws Exception {

    }

    @Test
    public void testIncreaseDownloadCount() throws Exception {

    }
}
