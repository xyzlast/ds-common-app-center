package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.auth.AcceptProgram;
import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import co.kr.daesung.app.center.domain.entities.auth.ApiMethod;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 12/4/13
 * Time: 9:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StatisticsService {
    List<ApiMethod> sortApiMethodsByUsedCount();

    List<ApiKey> sortApiKeysByUsedCount(int limit);

    List<AcceptProgram> sortAcceptProgramsByUsedCount(int limit);
}
