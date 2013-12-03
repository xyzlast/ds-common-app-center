package co.kr.daesung.app.center.domain.repo.auth;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 12/3/13
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.entities.auth.ApiMethod;
import co.kr.daesung.app.center.domain.entities.auth.QApiMethod;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@TransactionConfiguration(defaultRollback = false)
public class ApiMethodRepositoryTest {
    @Autowired
    private ApiMethodRepository apiMethodRepository;

    @Test
    public void addApplications() {
        QApiMethod qApiMethod = QApiMethod.apiMethod;

        if(apiMethodRepository.count(qApiMethod.name.eq(ApiMethod.GET_SIDOLIST)) == 0) {
            ApiMethod apiMethod = new ApiMethod();
            apiMethod.setName(ApiMethod.GET_SIDOLIST);
            apiMethod.setUrls(String.format("%s;%s", ApiMethod.API_ADDRESS_SIDO_LIST, ApiMethod.API_ADDRESS_SIDO_LIST_OLD));
            apiMethod.setUsedCount(0);
            apiMethod.setEnabled(true);
            apiMethodRepository.save(apiMethod);
        }

        if(apiMethodRepository.count(qApiMethod.name.eq(ApiMethod.GET_SIGUNGU)) == 0) {
            ApiMethod apiMethod = new ApiMethod();
            apiMethod.setName(ApiMethod.GET_SIGUNGU);
            apiMethod.setUrls(String.format("%s;%s", ApiMethod.API_ADDRESS_SIGUNGU_LIST, ApiMethod.API_ADDRESS_SIGUNGU_LIST_OLD));
            apiMethod.setUsedCount(0);
            apiMethod.setEnabled(true);
            apiMethodRepository.save(apiMethod);
        }

        if(apiMethodRepository.count(qApiMethod.name.eq(ApiMethod.SEARCH_JIBEON_ADDRESS)) == 0) {
            ApiMethod apiMethod = new ApiMethod();
            apiMethod.setName(ApiMethod.SEARCH_JIBEON_ADDRESS);
            apiMethod.setUrls(String.format("%s;%s", ApiMethod.API_ADDRESS_SEARCH_BY_JIBEON, ApiMethod.API_ADDRESS_SEARCH_BY_JIBEON_OLD));
            apiMethod.setUsedCount(0);
            apiMethod.setEnabled(true);
            apiMethodRepository.save(apiMethod);
        }

        if(apiMethodRepository.count(qApiMethod.name.eq(ApiMethod.SEARCH_ROAD_ADDRESS)) == 0) {
            ApiMethod apiMethod = new ApiMethod();
            apiMethod.setName(ApiMethod.SEARCH_ROAD_ADDRESS);
            apiMethod.setUrls(String.format("%s;%s", ApiMethod.API_ADDRESS_SEARCH_BY_ROAD, ApiMethod.API_ADDRESS_SEARCH_BY_ROAD_OLD));
            apiMethod.setUsedCount(0);
            apiMethod.setEnabled(true);
            apiMethodRepository.save(apiMethod);
        }

        if(apiMethodRepository.count(qApiMethod.name.eq(ApiMethod.SEARCH_BUILDING_ADDRESS)) == 0) {
            ApiMethod apiMethod = new ApiMethod();
            apiMethod.setName(ApiMethod.SEARCH_BUILDING_ADDRESS);
            apiMethod.setUrls(String.format("%s;%s", ApiMethod.API_ADDRESS_SEARCH_BY_BUILDING, ApiMethod.API_ADDRESS_SEARCH_BY_BUILDING_OLD));
            apiMethod.setUsedCount(0);
            apiMethod.setEnabled(true);
            apiMethodRepository.save(apiMethod);
        }

        if(apiMethodRepository.count(qApiMethod.name.eq(ApiMethod.SEND_CREW_MESSAGE)) == 0) {
            ApiMethod apiMethod = new ApiMethod();
            apiMethod.setName(ApiMethod.SEND_CREW_MESSAGE);
            apiMethod.setUrls(String.format("%s;%s", ApiMethod.API_MESSAGE_SEND_CREW, ApiMethod.API_MESSAGE_SEND_CREW_OLD));
            apiMethod.setUsedCount(0);
            apiMethod.setEnabled(true);
            apiMethodRepository.save(apiMethod);
        }

        if(apiMethodRepository.count(qApiMethod.name.eq(ApiMethod.SEND_NLEADER_MESSAGE)) == 0) {
            ApiMethod apiMethod = new ApiMethod();
            apiMethod.setName(ApiMethod.SEND_NLEADER_MESSAGE);
            apiMethod.setUrls(String.format("%s;%s", ApiMethod.API_MESSAGE_SEND_NLEADER, ApiMethod.API_MESSAGE_SEND_NLEADER_OLD));
            apiMethod.setUsedCount(0);
            apiMethod.setEnabled(true);
            apiMethodRepository.save(apiMethod);
        }


    }
}
