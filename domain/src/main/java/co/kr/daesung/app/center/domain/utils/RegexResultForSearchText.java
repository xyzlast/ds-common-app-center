package co.kr.daesung.app.center.domain.utils;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/8/13
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegexResultForSearchText {
    private String mainText;
    private Integer mainNumber;

    public RegexResultForSearchText(String mainText, Integer mainNumber) {
        this.mainText = mainText;
        this.mainNumber = mainNumber;
    }

    public String getMainText() {
        return mainText;
    }

    public Integer getMainNumber() {
        return mainNumber;
    }
}
