package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.app.App;
import co.kr.daesung.app.center.domain.entities.app.AppVersion;
import co.kr.daesung.app.center.domain.repo.app.AppFileRepository;
import co.kr.daesung.app.center.domain.repo.app.AppRepository;
import co.kr.daesung.app.center.domain.repo.app.AppVersionRepository;
import co.kr.daesung.app.center.domain.utils.AppFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/13/13
 * Time: 10:32 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class AppServiceImpl implements AppService {
    @Autowired
    private AppRepository appRepository;
    @Autowired
    private AppVersionRepository appVersionRepository;
    @Autowired
    private AppFileRepository appFileRepository;

    @Override
    public App createNewApp(String appName, String description, String registerUserId) {
        App app = appRepository.findOne(appName);
        if(app != null) {
            app.setDeleted(false);
        } else {
            app = new App();
            app.setName(appName);
        }
        app.setDescription(description);
        app.setRegisterUserId(registerUserId);
        appRepository.save(app);
        return app;
    }

    @Override
    public AppVersion addNewAppVersion(String appName, String version, String description, String registerUserId, List<AppFileItem> fileItems) {
        App app = appRepository.findOne(appName);
        if(app == null) {
            throw new IllegalArgumentException("App is not found!");
        }
        AppVersion appVersion = new AppVersion();
        appVersion.setApp(app);
        appVersion.setVersion(version);
        appVersion.setDescription(description);
        appVersion.setRegisterUser(registerUserId);

        return null;
    }

    @Override
    public AppVersion editAppVersion(int versionId, boolean top, String version, String description) {
        AppVersion appVersion = appVersionRepository.findOne(versionId);
        appVersion.setTop(top);
        appVersion.setVersion(version);
        appVersion.setDescription(description);
        appVersionRepository.save(appVersion);
        return appVersion;
    }

    @Override
    public AppVersion setTopVersion(int versionId) {
        AppVersion appVersion = appVersionRepository.findOne(versionId);
        appVersion.setTop(true);
        App app = appVersion.getApp();
        for(AppVersion appVersionItem : app.getVersions()) {
            appVersionItem.setTop(appVersionItem.getId() == versionId);
        }
        appRepository.save(app);
        return appVersion;
    }

    @Override
    public App getApp(String appName) {
        App app = appRepository.findOne(appName);
        if(app == null) {
            throw new IllegalArgumentException("app is not found!");
        }
        return app;
    }

    @Override
    public List<App> getApps() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void increaseDownloadCount(int appVersionId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteApp(String appName) {
        App app = appRepository.findOne(appName);
        if(app == null) {
            throw new IllegalArgumentException("app is not found!");
        }
        app.setDeleted(true);
        appRepository.save(app);
    }
}
