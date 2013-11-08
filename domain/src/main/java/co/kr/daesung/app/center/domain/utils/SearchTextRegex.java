package co.kr.daesung.app.center.domain.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/8/13
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchTextRegex {
    public static Matcher extractSearchText(String searchText) {
        String regex = "^(?<mainText>[^\\s]*)\\s?(?<mainNumber>\\d*)[^\\d]*$";
        Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(searchText);
        return matcher;
    }

    public static RegexResultForSearchText getSearchTextItem(String searchText) {
        Matcher matcher = extractSearchText(searchText);
        if(matcher.find()) {
            if(matcher.group("mainNumber").equals("")) {
                return new RegexResultForSearchText(matcher.group("mainText"), null);
            } else {
                return new RegexResultForSearchText(matcher.group("mainText"),
                        Integer.parseInt(matcher.group("mainNumber")));
            }

        } else {
            return new RegexResultForSearchText(searchText, null);
        }
    }
}
