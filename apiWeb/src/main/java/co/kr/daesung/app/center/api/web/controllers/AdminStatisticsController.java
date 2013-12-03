package co.kr.daesung.app.center.api.web.controllers;

import co.kr.daesung.app.center.api.web.aops.ResultDataFormat;
import co.kr.daesung.app.center.api.web.vos.AcceptProgramItem;
import co.kr.daesung.app.center.api.web.vos.ApiKeyItem;
import co.kr.daesung.app.center.domain.entities.auth.AcceptProgram;
import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import co.kr.daesung.app.center.domain.services.ApiKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 12/2/13
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class AdminStatisticsController {

    @Autowired
    private ApiKeyService apiKeyService;

    @RequestMapping(value = "/api/admin/statistics/apiKey", method = RequestMethod.GET)
    @ResultDataFormat
    @ResponseBody
    public Object getTopUsedApiKeys(int pageIndex, int pageSize) {
        Page<ApiKey> topUsedApiKeys = apiKeyService.getTopUsedApiKeys(pageIndex, pageSize);
        List<ApiKeyItem> result = new ArrayList<>();
        for(ApiKey apiKey : topUsedApiKeys) {
            result.add(new ApiKeyItem(apiKey, apiKeyService.getAcceptPrograms(apiKey.getId())));
        }
        return result;
    }

    @RequestMapping(value = "/api/admin/statistics/programs", method = RequestMethod.GET)
    @ResultDataFormat
    @ResponseBody
    public Object getTopUsedPrograms(int pageIndex, int pageSize) {
        final Page<AcceptProgram> topUsedPrograms = apiKeyService.getTopUsedPrograms(pageIndex, pageSize);
        List<AcceptProgramItem> result = new ArrayList<>();
        for(AcceptProgram program : topUsedPrograms) {
            result.add(new AcceptProgramItem(program));
        }
        return result;
    }
}
