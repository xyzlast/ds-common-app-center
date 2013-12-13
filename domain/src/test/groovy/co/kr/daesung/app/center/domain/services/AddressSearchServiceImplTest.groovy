package co.kr.daesung.app.center.domain.services

import co.kr.daesung.app.center.domain.configs.DomainConfiguration
import co.kr.daesung.app.center.domain.constants.SidoEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by ykyoon on 12/11/13.
 */
@ContextConfiguration(classes = [ DomainConfiguration ])
class AddressSearchServiceImplTest extends Specification {
    @Autowired
    AddressSearchService service;

    def setup() {

    }

    def "시, 도 리스트 얻어오기"() {
        when:
        def sidoList = service.getSiDoList();
        then:
        sidoList.size() == 17
    }

    def "빌딩 이름으로 검색하기"() {
        def buildingName = "레미안"
        when:
        def searchResults = service.searchByBuilding(buildingName)
        then:
        searchResults.every { r->
            r.buildingName.contains(buildingName) == true
        }
    }

    def "도로명으로 검색하기"() {
        def roadName = "지봉로"
        when:
        def searchResults = service.searchByRoad("11", roadName, true, 0, 100)
        then: '도로명은 모두 #roadName을 포함하고 있어야지 된다.'
        searchResults.every { r->
            r.road.roadName.contains(roadName) == true
        }
    }

    def "도로번호 얻어오기"() {
        def roadName = "역곡"
        when:
        def roads = service.getRoadNumbers(roadName);
        then:
        roads.every { r ->
           r.roadName.contains(roadName) == true
        }
    }

    def "건물이름 길이가 작아 exception이 발생"() {
        def buildingName = "가"
        when:
        service.searchByBuilding(buildingName)
        then:
        def e = thrown(IllegalArgumentException)
        e.message == AddressSearchServiceImpl.BUILDING_NAME_IS_TOO_SHORT
    }

    def "서울내의 레미안만 얻어오기"() {
        def buildingName = "레미안"
        when:
        def searchResults = service.searchByBuilding(SidoEnum.SEOUL.getStringValue(), buildingName, 0, 10)
        then:
        searchResults.every { r ->
            r.buildingName.contains(buildingName)
            r.siGunGu.sido.sidoNumber.equals(SidoEnum.SEOUL.getStringValue()) == true
        }
    }

    def "지번으로 신도림동을 얻어오기"() {
        def jibeonName = "신도림동"
        when:
        def searchResults = service.searchByJibeon(jibeonName, true, 0, 100)
        then:
        searchResults.every { r->
            r.toJibeonAddress(true).contains(jibeonName) == true
        }
    }
}
