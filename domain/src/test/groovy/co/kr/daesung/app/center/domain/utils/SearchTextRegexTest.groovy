package co.kr.daesung.app.center.domain.utils

import spock.lang.Specification

/**
 * Created by ykyoon on 12/11/13.
 */
class SearchTextRegexTest2 extends Specification {

    def "검색문자열 분리 테스트"() {
        expect:
        def matcher = SearchTextRegex.extractSearchText(input)
        matcher.find() == true
        matcher.group("mainText").equals(mainText) == true
        matcher.group("mainNumber").equals(mainNumber) == true

        where:
        input | mainText | mainNumber
        "구로동 245 번지" | "구로동" | "245"
        "서초1동" | "서초1동" | ""
        "잠실동 624" | "잠실동" | "624"
        "지봉로 1" | "지봉로" | "1"
    }

    def "검색문자열 분리 테스트2"() {
        expect:
        def result = SearchTextRegex.getSearchTextItem(input)
        result.mainText.equals(mainText) == true
        result.mainNumber == mainNumber
        where:
        input | mainText | mainNumber
        "구로동 245 번지" | "구로동" | 245
        "서초1동" | "서초1동" | null
        "잠실동 624" | "잠실동" | 624
        "지봉로 1" | "지봉로" | 1
    }
}
