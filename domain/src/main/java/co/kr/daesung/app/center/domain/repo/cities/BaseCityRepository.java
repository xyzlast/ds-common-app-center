package co.kr.daesung.app.center.domain.repo.cities;

import co.kr.daesung.app.center.domain.entities.support.BaseAddress;
import co.kr.daesung.app.center.domain.repo.BaseRepository;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/4/13
 * Time: 5:24 PM
 * Base city Repository
 */
public interface BaseCityRepository<T extends BaseAddress> extends BaseRepository<T, String> {

}
