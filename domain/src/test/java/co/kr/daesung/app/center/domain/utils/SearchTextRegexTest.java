package co.kr.daesung.app.center.domain.utils;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/8/13
 * Time: 4:53 PM
 * To change this template use File | Settings | File Templates.
 */

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.regex.Matcher;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

public class SearchTextRegexTest {
    @Test
    public void testExtractSearchText() throws Exception {
        String sample1 = "구로동 245 번지";
        Matcher matcher1 = SearchTextRegex.extractSearchText(sample1);
        assertThat(matcher1.find(), is(true));
        assertThat(matcher1.group("mainText"), is("구로동"));
        assertThat(matcher1.group("mainNumber"), is("245"));

        String sample2 = "서초1동";
        Matcher matcher2 = SearchTextRegex.extractSearchText(sample2);
        assertThat(matcher2.find(), is(true));
        assertThat(matcher2.group("mainText"), is("서초1동"));
        assertThat(matcher2.group("mainNumber"), is(""));

        String sample3 = "잠실동 624";
        Matcher matcher3 = SearchTextRegex.extractSearchText(sample3);
        assertThat(matcher3.find(), is(true));
        assertThat(matcher3.group("mainText"), is("잠실동"));
        assertThat(matcher3.group("mainNumber"), is("624"));

        String sample4 = "지봉로 1";
        Matcher matcher4 = SearchTextRegex.extractSearchText(sample4);
        assertThat(matcher4.find(), is(true));
        assertThat(matcher4.group("mainText"), is("지봉로"));
        assertThat(matcher4.group("mainNumber"), is("1"));
    }

    @Test
    public void testGetSearchItemResult() {
        String sample1 = "지봉로 1";
        final RegexResultForSearchText searchTextItem = SearchTextRegex.getSearchTextItem(sample1);
        assertThat(searchTextItem.getMainText(), is("지봉로"));
        assertThat(searchTextItem.getMainNumber(), is(1));
    }
}
