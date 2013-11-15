package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.app.App;
import co.kr.daesung.app.center.domain.entities.app.AppVersion;
import co.kr.daesung.app.center.domain.utils.AppFileItem;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/13/13
 * Time: 10:25 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AppService {
    App createNewApp(String appName, String description, String registerUserId);
    AppVersion addNewAppVersion(String appName, String version, String description, String registerUserId, List<AppFileItem> fileItems);
    AppVersion editAppVersion(int versionId, boolean top, String version, String description);
    AppVersion setTopVersion(int versionId);
    App getApp(String appName);

    List<App> getApps();
    void increaseDownloadCount(int appVersionId);
    void deleteApp(String appName);
}
