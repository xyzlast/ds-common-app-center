package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.auth.AcceptProgram;
import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import co.kr.daesung.app.center.domain.entities.auth.QAcceptProgram;
import co.kr.daesung.app.center.domain.entities.auth.QApiKey;
import co.kr.daesung.app.center.domain.repo.auth.AcceptProgramRepository;
import co.kr.daesung.app.center.domain.repo.auth.ApiKeyRepository;
import com.mysema.query.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/9/13
 * Time: 1:47 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class ApiKeyServiceImpl implements ApiKeyService {
    @Autowired
    private ApiKeyRepository apiKeyRepository;
    @Autowired
    private AcceptProgramRepository acceptProgramRepository;

    @Override
    public Page<ApiKey> getApiKeys(String userId, int pageIndex, int pageSize) {
        QApiKey qApiKey = QApiKey.apiKey;
        Predicate predicate = qApiKey.userId.eq(userId);
        PageRequest pageRequest = new PageRequest(pageIndex, pageSize);

        return apiKeyRepository.findAll(predicate, pageRequest);
    }

    @Override
    public ApiKey generateNewKey(String userId) {
        ApiKey apiKey = new ApiKey();
        apiKey.setUserId(userId);
        apiKeyRepository.save(apiKey);
        return apiKey;
    }

    @Override
    public ApiKey getApiKey(String id) {
        return apiKeyRepository.findOne(id);
    }

    @Override
    public boolean deleteKey(String id) {
        ApiKey apiKey = apiKeyRepository.findOne(id);
        apiKey.setDeleted(true);
        apiKeyRepository.save(apiKey);
        return true;
    }

    @Override
    public AcceptProgram addProgramTo(ApiKey apiKey, String program, String description) {
        QAcceptProgram qAcceptProgram = QAcceptProgram.acceptProgram;

        Predicate predicate = qAcceptProgram.apiKey.eq(apiKey)
                .and(qAcceptProgram.program.equalsIgnoreCase(program))
                .and(qAcceptProgram.deleted.isFalse());
        long count = acceptProgramRepository.count(predicate);

        if(count == 0) {
            AcceptProgram acceptProgram = new AcceptProgram();
            acceptProgram.setApiKey(apiKey);
            acceptProgram.setProgram(program);
            acceptProgram.setDescription(description);
            acceptProgramRepository.save(acceptProgram);
            return acceptProgram;
        } else {
            throw new IllegalArgumentException("Program is already accepted!");
        }
    }

    @Override
    public boolean removeProgramFrom(ApiKey apiKey, String programName) {
        QAcceptProgram qAcceptProgram = QAcceptProgram.acceptProgram;
        Predicate predicate = qAcceptProgram.apiKey.eq(apiKey)
                .and(qAcceptProgram.program.eq(programName))
                .and(qAcceptProgram.deleted.isFalse());
        AcceptProgram acceptProgram = acceptProgramRepository.findOne(predicate);
        if(acceptProgram == null) {
            throw new IllegalArgumentException("Program matched is not found!");
        } else {
            acceptProgram.setDeleted(true);
            acceptProgramRepository.save(acceptProgram);
        }
        return true;
    }

    @Override
    public boolean removeProgramFrom(ApiKey apiKey, int programId) {
        AcceptProgram acceptProgram = acceptProgramRepository.findOne(programId);
        if(acceptProgram == null ||
                !acceptProgram.getApiKey().getId().equals(apiKey.getId())) {
            throw new IllegalArgumentException("Program matched is not found!");
        } else {
            acceptProgram.setDeleted(true);
            acceptProgramRepository.save(acceptProgram);
        }
        return true;
    }

    @Override
    public boolean isAcceptedKey(ApiKey apiKey, String program) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Page<ApiKey> getTopUsedApiKeys(int pageIndex, int pageSize) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Page<AcceptProgram> getTopUsedPrograms(int pageIndex, int pageSize) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
