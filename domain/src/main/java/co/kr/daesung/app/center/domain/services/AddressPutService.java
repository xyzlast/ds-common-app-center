package co.kr.daesung.app.center.domain.services;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/16/13
 * Time: 2:40 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AddressPutService {
    boolean clearAllAddresses();
    // 행정안전부에서 매월 제공하는 대표지번 파일을 이용하여 데이터베이스에 Insert, 시/도, 읍면동, 길
    boolean insertBaseDataFromFile(String fileFullName, String encoding);
    // 행정안전부에서 매월 제공하는 대표지번 파일을 이용하여 데이터베이스에 Insert, 각 Address. 반드시 insertBaseDataFromFile 후, 처리되어야지 됨 - Transaction 문제로 인하여 두개로 처리
    boolean insertAddressFromFile(String fileFullName, String encoding);
}
