package co.kr.daesung.app.center.domain.services

import co.kr.daesung.app.center.domain.configs.DomainConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by ykyoon on 12/11/13.
 */
@ContextConfiguration(classes = [ DomainConfiguration.class ])
class StatisticsServiceImplTest extends Specification {

    @Autowired
    StatisticsService service

    def setup() {

    }

    def '많이 사용한 Api 순으로 정렬'() {
        when:
        def results = service.sortApiMethodsByUsedCount()
        then: '가장 많은 값을 가진 Item이 List의 최 상단에, 가장 작은 값을 가진 Item이 List의 최하단에 나와야지 된다.'
        results.size() != 0
        def maxUsedItem = results.max {
            it.usedCount
        }
        def minUsedItem = results.min {
            it.usedCount
        }
        maxUsedItem.usedCount == results[0].usedCount
        minUsedItem.usedCount == results.reverse()[0].usedCount
    }

    def '많이 사용한 ApiKey를 정렬'() {
        when:
        def results = service.sortApiKeysByUsedCount(5)
        then:
        results.size() <= 5
        def maxUsedItem = results.max {
            it.usedCount
        }
        def minUsedItem = results.min {
            it.usedCount
        }
        maxUsedItem.usedCount == results[0].usedCount
        minUsedItem.usedCount == results.reverse()[0].usedCount
    }

    def '많이 사용한 Program들을 정렬'() {
        when:
        def results = service.sortAcceptProgramsByUsedCount(5)
        then:
        results.size() <= 5
        def maxUsedItem = results.max {
            it.usedCount
        }
        def minUsedItem = results.min {
            it.usedCount
        }
        maxUsedItem.usedCount == results[0].usedCount
        minUsedItem.usedCount == results.reverse()[0].usedCount
    }
}
