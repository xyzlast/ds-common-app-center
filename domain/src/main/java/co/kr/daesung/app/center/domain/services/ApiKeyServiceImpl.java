package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.auth.AcceptProgram;
import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import co.kr.daesung.app.center.domain.entities.auth.QAcceptProgram;
import co.kr.daesung.app.center.domain.entities.auth.QApiKey;
import co.kr.daesung.app.center.domain.repo.auth.AcceptProgramRepository;
import co.kr.daesung.app.center.domain.repo.auth.ApiKeyRepository;
import co.kr.daesung.app.center.domain.utils.ArrayUtil;
import com.mysema.query.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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

    private static final String PERMISSION_NOT_MATCHED = "this user do not have this permission";

    private void checkAccessable(String userId, ApiKey apiKey) throws IllegalAccessException {
        if(!apiKey.getUserId().equals(userId)) {
            throw new IllegalAccessException(PERMISSION_NOT_MATCHED);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApiKey> getApiKeys(String userId, int pageIndex, int pageSize) {
        QApiKey qApiKey = QApiKey.apiKey;
        Predicate predicate = qApiKey.userId.eq(userId).and(qApiKey.deleted.isFalse());
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
    @Transactional(readOnly = true)
    public ApiKey getApiKey(String id) {
        return apiKeyRepository.findOne(id);
    }

    @Override
    public boolean deleteKey(String userId, String id) throws IllegalAccessException {
        ApiKey apiKey = apiKeyRepository.findOne(id);
        checkAccessable(userId, apiKey);
        apiKey.setDeleted(true);
        apiKeyRepository.save(apiKey);
        return true;
    }

    @Override
    public AcceptProgram addProgramTo(String userId, ApiKey apiKey, String program, String description)
            throws IllegalAccessException {

        if(program == null || program.length() == 0) {
            throw new IllegalArgumentException("ProgramName is empty!");
        }
        QAcceptProgram qAcceptProgram = QAcceptProgram.acceptProgram;
        checkAccessable(userId, apiKey);

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
            throw new IllegalArgumentException(PERMISSION_NOT_MATCHED);
        }
    }

    @Override
    public AcceptProgram getAcceptProgram(int programId) {
        return acceptProgramRepository.findOne(programId);
    }

    @Override
    public boolean removeProgramFrom(String userId, ApiKey apiKey, String programName)
            throws IllegalAccessException {
        checkAccessable(userId, apiKey);

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
    public boolean removeProgramFrom(String userId, int programId) throws IllegalAccessException {
        AcceptProgram acceptProgram = acceptProgramRepository.findOne(programId);
        ApiKey apiKey = acceptProgram.getApiKey();
        checkAccessable(userId, apiKey);
        acceptProgram.setDeleted(true);
        acceptProgramRepository.save(acceptProgram);
        return true;
    }

    @Override
    @Transactional
    public boolean isAcceptedKey(ApiKey apiKey, String program) {
        QAcceptProgram qAcceptProgram = QAcceptProgram.acceptProgram;
        Predicate predicate = qAcceptProgram.apiKey.eq(apiKey)
                .and(qAcceptProgram.apiKey.deleted.isFalse())
                .and(qAcceptProgram.program.eq(program))
                .and(qAcceptProgram.deleted.isFalse());

        boolean isAccepted = acceptProgramRepository.count(predicate) == 1;
        if(isAccepted) {
            apiKey.setUsedCount(apiKey.getUsedCount() + 1);
            apiKeyRepository.save(apiKey);

            AcceptProgram acceptProgram = acceptProgramRepository.findOne(predicate);
            acceptProgram.increaseCallTime();
            acceptProgramRepository.save(acceptProgram);
        }
        return isAccepted;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isAcceptedKey(String apiKeyId, String program) {
        ApiKey apiKey = apiKeyRepository.findOne(apiKeyId);
        if(apiKey == null || apiKey.isDeleted()) {
            return false;
        } else {
            return isAcceptedKey(apiKey, program);
        }
    }

    @Override
    public List<AcceptProgram> getAcceptPrograms(String apiKeyId) {
        QAcceptProgram qAcceptProgram = QAcceptProgram.acceptProgram;
        Predicate predicate = qAcceptProgram.apiKey.id.eq(apiKeyId).and(qAcceptProgram.deleted.isFalse());
        return ArrayUtil.convertTo(acceptProgramRepository.findAll(predicate));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApiKey> getTopUsedApiKeys(int pageIndex, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.DESC, "usedCount"));
        return apiKeyRepository.findAll(pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AcceptProgram> getTopUsedPrograms(int pageIndex, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.DESC, "usedCount"));
        return acceptProgramRepository.findAll(pageRequest);
    }
}
