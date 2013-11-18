package co.kr.daesung.app.center.api.web.controllers;

import co.kr.daesung.app.center.api.web.aops.Jsonp;
import co.kr.daesung.app.center.api.web.aops.ResultDataFormat;
import co.kr.daesung.app.center.api.web.auth.UserInfoHelper;
import co.kr.daesung.app.center.api.web.vos.ApiKeyItem;
import co.kr.daesung.app.center.api.web.vos.ResultData;
import co.kr.daesung.app.center.domain.entities.auth.ApiKey;
import co.kr.daesung.app.center.domain.entities.auth.User;
import co.kr.daesung.app.center.domain.services.ApiKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @Autowired
    private UserInfoHelper userInfoHelper;
    @Autowired
    private ApiKeyService apiKeyService;

    @RequestMapping(value = "/api/authKey/getTempData")
    public Object getTempData() {
        ResultData r = new ResultData(new IllegalArgumentException("Test Exception"));
        return r;
    }

    @RequestMapping(value = "/api/authKey/getApiKeys")
    @ResultDataFormat
    @Jsonp
    public Object getApiKeyList(HttpServletRequest request, HttpServletResponse response, int pageIndex, int pageSize) {
        User user = userInfoHelper.getUserFromRequest(request);
        final Page<ApiKey> apiKeys = apiKeyService.getApiKeys(user.getUsername(), pageIndex, pageSize);
        List<ApiKeyItem> result = new ArrayList<>();
        for(ApiKey apiKey : apiKeys) {
            result.add(new ApiKeyItem(apiKey));
        }
        return result;
    }

    
}
