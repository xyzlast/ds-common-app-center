package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/16/13
 * Time: 2:47 AM
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DomainConfiguration.class })
public class AddressPutServiceImplTest {

    @Autowired
    private AddressPutService addressPutService;

    @Test
    public void testClearAllAddresses() throws Exception {
        // addressPutService.clearAllAddresses();
    }

    @Test
    public void testInsertBaseDataFromFile() throws Exception {

    }

    @Test
    public void testInsertAddressFromFile() throws Exception {

    }
}
