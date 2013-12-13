package co.kr.daesung.app.center.domain.repo.auth;

import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.entities.auth.ApiMethod;
import co.kr.daesung.app.center.domain.entities.auth.QApiMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Created by ykyoon on 12/11/13.
 * add default api list to database
 */
public class ApiMethodsInitializer {

    private static void checkAndSaveApiMethod(ApiMethodRepository repo, String apiMethodName, String... urls) {
        QApiMethod qApiMethod = QApiMethod.apiMethod;
        if(repo.count(qApiMethod.name.eq(apiMethodName)) == 0) {
            ApiMethod apiMethod = new ApiMethod();
            apiMethod.setName(ApiMethod.GET_SIDOLIST);
            String url = urls[0];
            for(int i = 1 ; i < urls.length ; i++) {
                url = url + ";" + urls[i];
            }
            apiMethod.setUrls(url);
            apiMethod.setUsedCount(0);
            apiMethod.setEnabled(true);
            repo.save(apiMethod);
        }
    }


    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(DomainConfiguration.class);
        PlatformTransactionManager transactionManager = context.getBean(PlatformTransactionManager.class);
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(txDef);
        ApiMethodRepository repo = context.getBean(ApiMethodRepository.class);

        try {
            QApiMethod qApiMethod = QApiMethod.apiMethod;
            checkAndSaveApiMethod(repo, ApiMethod.GET_SIDOLIST, ApiMethod.API_ADDRESS_SIDO_LIST, ApiMethod.API_ADDRESS_SIDO_LIST_OLD);
            checkAndSaveApiMethod(repo, ApiMethod.GET_SIGUNGU, ApiMethod.API_ADDRESS_SIGUNGU_LIST, ApiMethod.API_ADDRESS_SIGUNGU_LIST_OLD);
            checkAndSaveApiMethod(repo, ApiMethod.SEARCH_JIBEON_ADDRESS, ApiMethod.API_ADDRESS_SEARCH_BY_JIBEON, ApiMethod.API_ADDRESS_SEARCH_BY_JIBEON_OLD);
            checkAndSaveApiMethod(repo, ApiMethod.SEARCH_ROAD_ADDRESS, ApiMethod.API_ADDRESS_SEARCH_BY_ROAD, ApiMethod.API_ADDRESS_SEARCH_BY_ROAD_OLD);
            checkAndSaveApiMethod(repo, ApiMethod.SEARCH_BUILDING_ADDRESS, ApiMethod.API_ADDRESS_SEARCH_BY_BUILDING, ApiMethod.API_ADDRESS_SEARCH_BY_BUILDING_OLD);
            checkAndSaveApiMethod(repo, ApiMethod.SEND_CREW_MESSAGE, ApiMethod.API_MESSAGE_SEND_CREW, ApiMethod.API_MESSAGE_SEND_CREW_OLD);
            checkAndSaveApiMethod(repo, ApiMethod.SEND_NLEADER_MESSAGE, ApiMethod.API_MESSAGE_SEND_NLEADER, ApiMethod.API_MESSAGE_SEND_NLEADER_OLD);
            transactionManager.commit(transactionStatus);
        } catch(Exception ex) {
            transactionManager.rollback(transactionStatus);
        }
    }
}
