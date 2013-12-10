package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.auth.AcceptProgram;
import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import co.kr.daesung.app.center.domain.entities.auth.ApiMethod;
import co.kr.daesung.app.center.domain.entities.auth.QApiMethod;
import co.kr.daesung.app.center.domain.repo.auth.AcceptProgramRepository;
import co.kr.daesung.app.center.domain.repo.auth.ApiKeyRepository;
import co.kr.daesung.app.center.domain.repo.auth.ApiMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 12/4/13
 * Time: 9:26 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional(readOnly = true)
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private ApiMethodRepository apiMethodRepository;
    @Autowired
    private ApiKeyRepository apiKeyRepository;
    @Autowired
    private AcceptProgramRepository acceptProgramRepository;

    @Override
    public List<ApiMethod> sortApiMethodsByUsedCount() {
        Sort sort = new Sort(Sort.Direction.DESC, "usedCount");
        return apiMethodRepository.findAll(sort);
    }

    @Override
    public List<ApiKey> sortApiKeysByUsedCount(int limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "usedCount");
        PageRequest pageRequest = new PageRequest(0, limit, sort);
        return apiKeyRepository.findAll(pageRequest).getContent();
    }

    @Override
    public List<AcceptProgram> sortAcceptProgramsByUsedCount(int limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "usedCount");
        PageRequest pageRequest = new PageRequest(0, limit, sort);
        return acceptProgramRepository.findAll(pageRequest).getContent();
    }
}
