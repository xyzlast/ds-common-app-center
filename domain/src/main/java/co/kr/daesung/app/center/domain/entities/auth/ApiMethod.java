package co.kr.daesung.app.center.domain.entities.auth;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 12/3/13
 * Time: 4:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Getter
@Setter
public class ApiMethod {
    public static final String GET_SIDOLIST = "SiDoList";
    public static final String API_ADDRESS_SIDO_LIST = "/api/address/sido/list";
    public static final String API_ADDRESS_SIDO_LIST_OLD = "/Api/Address/GetSiDoList";

    public static final String SEARCH_JIBEON_ADDRESS = "SearchJibeonAddress";
    public static final String API_ADDRESS_SEARCH_BY_JIBEON = "/api/address/search/jibeon";
    public static final String API_ADDRESS_SEARCH_BY_JIBEON_OLD = "/Api/Address/SearchByJibeon";

    public static final String SEARCH_ROAD_ADDRESS = "SearchRoadAddress";
    public static final String API_ADDRESS_SEARCH_BY_ROAD = "/api/address/search/road";
    public static final String API_ADDRESS_SEARCH_BY_ROAD_OLD = "/Api/Address/SearchByRoad";

    public static final String SEARCH_BUILDING_ADDRESS = "SearchBuildingAddress";
    public static final String API_ADDRESS_SEARCH_BY_BUILDING = "/api/address/search/building";
    public static final String API_ADDRESS_SEARCH_BY_BUILDING_OLD = "/Api/Address/SearchByBuildingName";

    public static final String GET_SIGUNGU = "SiGunGuList";
    public static final String API_ADDRESS_SIGUNGU_LIST = "/api/address/sigungu/list";
    public static final String API_ADDRESS_SIGUNGU_LIST_OLD = "/Api/Address/GetSiGunGuList";

    public static final String SEND_CREW_MESSAGE = "SendCrewMessage";
    public static final String API_MESSAGE_SEND_CREW = "/api/message/send/crew";
    public static final String API_MESSAGE_SEND_CREW_OLD = "/Api/Message/SendCrewMessage";

    public static final String SEND_NLEADER_MESSAGE = "SendNLeaderMessage";
    public static final String API_MESSAGE_SEND_NLEADER_OLD = "/Api/Message/SendNLeaderMessage";
    public static final String API_MESSAGE_SEND_NLEADER = "/api/message/send/nleader";

    public static final String API_MESSAGE_CREW_LIST = "/api/message/crew/list";
    public static final String API_MESSAGE_NLEADER_LIST = "/api/message/nleader/list";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = true)
    private String description;
    @Column(length = 255)
    private String urls;
    private long usedCount;
    private Date lastCallTime;
    private boolean enabled;

    public void increaseUsedCount() {
        System.out.println("increaseUsedCount");
        setUsedCount(getUsedCount() + 1);
        setLastCallTime(new Date());
    }
}
