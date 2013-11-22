package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.auth.AcceptProgram;
import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import org.springframework.data.domain.Page;

import java.util.List;

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
    boolean deleteKey(String userId, String id) throws IllegalAccessException;

    AcceptProgram addProgramTo(String userId, ApiKey apiKey, String program, String description) throws IllegalAccessException;
    AcceptProgram getAcceptProgram(int programId);
    boolean removeProgramFrom(String userId, ApiKey apiKey, String programName) throws IllegalAccessException;
    boolean removeProgramFrom(String userId, int programId) throws IllegalAccessException;
    boolean removeProgramFrom(String userId, String apiKeyId, int[] programIds) throws IllegalAccessException;
    boolean isAcceptedKey(ApiKey apiKey, String program);
    boolean isAcceptedKey(String apiKeyId, String program);

    List<AcceptProgram> getAcceptPrograms(String apiKeyId);
    Page<ApiKey> getTopUsedApiKeys(int pageIndex, int pageSize);
    Page<AcceptProgram> getTopUsedPrograms(int pageIndex, int pageSize);
}
