package co.kr.daesung.app.center.domain.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/8/13
 * Time: 9:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class ArrayUtil {
    public static <T> List<T> convertTo(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        Iterator<T> iterator = iterable.iterator();
        while(iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }
}
