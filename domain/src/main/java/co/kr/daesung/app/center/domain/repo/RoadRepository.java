package co.kr.daesung.app.center.domain.repo;

import co.kr.daesung.app.center.domain.entities.Road;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/4/13
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RoadRepository extends BaseRepository<Road, String> {
}
