package co.kr.daesung.app.center.api.web.controllers;

import co.kr.daesung.app.center.api.web.aops.ResultDataFormat;
import co.kr.daesung.app.center.api.web.vos.AcceptProgramItem;
import co.kr.daesung.app.center.api.web.vos.ApiKeyItem;
import co.kr.daesung.app.center.domain.entities.auth.AcceptProgram;
import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import co.kr.daesung.app.center.domain.services.ApiKeyService;
import co.kr.daesung.app.center.domain.services.StatisticsService;
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

    public static final String API_ADMIN_STATISTICS_API = "/api/admin/statistics/api";
    public static final String API_ADMIN_STATISTICS_PROGRAMS = "/api/admin/statistics/program";
    public static final String API_ADMIN_STATISTICS_API_KEY = "/api/admin/statistics/apiKey";

    @Autowired
    private ApiKeyService apiKeyService;
    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(value = API_ADMIN_STATISTICS_API, method = RequestMethod.GET)
    @ResultDataFormat
    @ResponseBody
    public Object getStatisticsForApiMethods() {
        return statisticsService.sortApiMethodsByUsedCount();
    }

    @RequestMapping(value = API_ADMIN_STATISTICS_API_KEY, method = RequestMethod.GET)
    @ResultDataFormat
    @ResponseBody
    public Object getTopUsedApiKeys(int limit) {
        List<ApiKey> apiKeys = statisticsService.sortApiKeysByUsedCount(limit);
        List<ApiKeyItem> result = new ArrayList<>();
        for(ApiKey apiKey : apiKeys) {
            result.add(new ApiKeyItem(apiKey, apiKeyService.getAcceptPrograms(apiKey.getId())));
        }
        return result;
    }

    @RequestMapping(value = API_ADMIN_STATISTICS_PROGRAMS, method = RequestMethod.GET)
    @ResultDataFormat
    @ResponseBody
    public Object getTopUsedPrograms(int limit) {
        List<AcceptProgram> acceptPrograms = statisticsService.sortAcceptProgramsByUsedCount(limit);
        List<AcceptProgramItem> result = new ArrayList<>();
        for(AcceptProgram program : acceptPrograms) {
            result.add(new AcceptProgramItem(program));
        }
        return result;
    }
}
