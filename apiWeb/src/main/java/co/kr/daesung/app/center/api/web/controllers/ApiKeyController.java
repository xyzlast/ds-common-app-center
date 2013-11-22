package co.kr.daesung.app.center.api.web.controllers;

import co.kr.daesung.app.center.api.web.aops.Jsonp;
import co.kr.daesung.app.center.api.web.aops.ResultDataFormat;
import co.kr.daesung.app.center.api.web.auth.UserInfoHelper;
import co.kr.daesung.app.center.api.web.vos.ApiKeyItem;
import co.kr.daesung.app.center.api.web.vos.ResultData;
import co.kr.daesung.app.center.domain.entities.auth.AcceptProgram;
import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import co.kr.daesung.app.center.domain.entities.auth.User;
import co.kr.daesung.app.center.domain.services.ApiKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ApiKeyController {

    public static final String GET_TEMP_DATA = "/api/apiKey/getTempData";
    public static final String API_API_KEY_LIST = "/api/apiKey/list";
    public static final String API_API_KEY_GENERATE = "/api/apiKey/generate";
    public static final String API_API_KEY_DELETE = "/api/apiKey/delete";
    public static final String API_API_KEY_PROGRAMS = "/api/apiKey/programs";
    public static final String API_API_KEY_PROGRAM_ADD = "/api/apiKey/program/add";
    public static final String API_API_KEY_PROGRAM_DELETE = "/api/apiKey/program/delete";

    @Autowired
    private ApiKeyService apiKeyService;

    @RequestMapping(value = GET_TEMP_DATA)
    @ResponseBody
    public Object getTempData() {
        ResultData r = new ResultData(new IllegalArgumentException("Test Exception"));
        return r;
    }

    @RequestMapping(value = API_API_KEY_LIST)
    @ResponseBody
    @ResultDataFormat
    @Jsonp
    public Object getApiKeyList(HttpServletRequest request, HttpServletResponse response, int pageIndex, int pageSize) {
        String username = request.getUserPrincipal().getName();
        final Page<ApiKey> apiKeys = apiKeyService.getApiKeys(username, pageIndex, pageSize);
        List<ApiKeyItem> result = new ArrayList<>();
        for(ApiKey apiKey : apiKeys) {
            final List<AcceptProgram> acceptPrograms = apiKeyService.getAcceptPrograms(apiKey.getId());
            result.add(new ApiKeyItem(apiKey, acceptPrograms));
        }
        return result;
    }

    @RequestMapping(value = API_API_KEY_GENERATE, method = RequestMethod.POST)
    @ResponseBody
    @ResultDataFormat
    @Jsonp
    public Object generateApiKey(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getUserPrincipal().getName();
        ApiKey apiKey = apiKeyService.generateNewKey(username);
        final List<AcceptProgram> acceptPrograms = apiKeyService.getAcceptPrograms(apiKey.getId());
        return new ApiKeyItem(apiKey, acceptPrograms);
    }

    @RequestMapping(value = API_API_KEY_DELETE, method = RequestMethod.POST)
    @ResponseBody
    @ResultDataFormat
    @Jsonp
    public Object deleteApiKey(HttpServletRequest request, HttpServletResponse response, String apiKeyId)
            throws IllegalAccessException {
        Boolean result = apiKeyService.deleteKey(request.getUserPrincipal().getName(), apiKeyId);
        return result;
    }

    @RequestMapping(value = API_API_KEY_PROGRAMS, method = RequestMethod.GET)
    @ResponseBody
    @ResultDataFormat
    @Jsonp
    public Object getPrograms(HttpServletRequest request, HttpServletResponse response, String apiKeyId) {
        return apiKeyService.getAcceptPrograms(apiKeyId);
    }

    @RequestMapping(value = API_API_KEY_PROGRAM_ADD, method = RequestMethod.POST)
    @ResponseBody
    @ResultDataFormat
    @Jsonp
    public Object addProgram(HttpServletRequest request, HttpServletResponse response,
                             String apiKeyId, String programName, String programDescription) throws IllegalAccessException {
        ApiKey apiKey = apiKeyService.getApiKey(apiKeyId);
        return apiKeyService.addProgramTo(request.getUserPrincipal().getName(),
                apiKey, programName, programDescription);
    }

    @RequestMapping(value = API_API_KEY_PROGRAM_DELETE, method = RequestMethod.POST)
    @ResponseBody
    @ResultDataFormat
    @Jsonp
    public Object deleteProgram(HttpServletRequest request, HttpServletResponse response,
                                String apiKeyId,
                                int[] programIds) throws IllegalAccessException {
        return Boolean.valueOf(apiKeyService
                .removeProgramFrom(request.getUserPrincipal().getName(), apiKeyId, programIds));
    }
}
