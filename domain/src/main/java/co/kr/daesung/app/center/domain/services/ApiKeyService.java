package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.auth.AcceptProgram;
import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import org.springframework.data.domain.Page;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/9/13
 * Time: 1:41 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ApiKeyService {
    Page<ApiKey> getApiKeys(String userId, int pageIndex, int pageSize);

    ApiKey generateNewKey(String userId);
    ApiKey getApiKey(String id);
    boolean deleteKey(String id);

    AcceptProgram addProgramTo(ApiKey apiKey, String program, String description);
    boolean removeProgramFrom(ApiKey apiKey, String programName);
    boolean removeProgramFrom(ApiKey apiKey, int programId);
    boolean isAcceptedKey(ApiKey apiKey, String program);

    Page<ApiKey> getTopUsedApiKeys(int pageIndex, int pageSize);
    Page<AcceptProgram> getTopUsedPrograms(int pageIndex, int pageSize);
}
