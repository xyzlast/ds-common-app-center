package co.kr.daesung.app.center.domain.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/13/13
 * Time: 10:28 AM
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class AppFileItem {
    private String fileName;
    private String localFileName;

    public String getContentType() {
        if(fileName.endsWith(".ipa")) {
            return "application/octet-stream";
        } else if(fileName.endsWith(".plist")) {
            return "text/xml";
        } else {
            return "";
        }
    }
}
