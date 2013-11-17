package co.kr.daesung.app.center.domain.services;

import co.kr.daesung.app.center.domain.entities.address.EupMyeonDongRi;
import co.kr.daesung.app.center.domain.entities.address.Road;
import co.kr.daesung.app.center.domain.entities.address.SiDo;
import co.kr.daesung.app.center.domain.entities.address.SiGunGu;
import co.kr.daesung.app.center.domain.entities.address.cities.*;
import co.kr.daesung.app.center.domain.entities.address.support.BaseAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/16/13
 * Time: 2:40 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AddressPutServiceImpl implements AddressPutService {
    @Autowired
    private EntityManagerFactory emf;
    private static final int CONSOLE_DISPLAY_PER_COUNT = 2000;
    private static final int SESSION_FLUSH_ITEM_COUNT = 100;

    private void executeNativeQuery(EntityManager em, String query) {
        System.out.println(query);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.createNativeQuery(query)
                .setFlushMode(FlushModeType.COMMIT)
                .setLockMode(LockModeType.NONE)
                .executeUpdate();
        transaction.commit();
    }

    @Override
    public boolean clearAllAddresses() {
        String[] deleteQueries = new String[] {
                "DELETE FROM Busan",
                "DELETE FROM ChungcheongBukdo",
                "DELETE FROM ChungcheongNamdo",
                "DELETE FROM Daegu",
                "DELETE FROM Daejeon",
                "DELETE FROM Gangwondo",
                "DELETE FROM Gwangju",
                "DELETE FROM Gyeonggido",
                "DELETE FROM GyeongsangBukdo",
                "DELETE FROM GyeongsangNamdo",
                "DELETE FROM Incheon",
                "DELETE FROM Jejudo",
                "DELETE FROM JeollaBukdo",
                "DELETE FROM JeollaNamdo",
                "DELETE FROM Sejong",
                "DELETE FROM Seoul",
                "DELETE FROM Ulsan",
                "DELETE FROM EupMyeonDongRi",
                "DELETE FROM Road",
                "DELETE FROM SiGunGu",
                "DELETE FROM SiDo"
        };

        EntityManager em = emf.createEntityManager();
        for(String query : deleteQueries) {
            executeNativeQuery(em, query);
        }

        return true;
    }

    private Object merge(EntityManager em, Object obj) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        obj = em.merge(obj);
        transaction.commit();
        return obj;
    }

    @Override
    public boolean insertBaseDataFromFile(String fileFullName, String encoding) {
        EntityManager em = emf.createEntityManager();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(fileFullName), encoding));
            String line;
            int count = 0;
            SiDo sido = null;
            while((line = bf.readLine()) != null) {
                String[] items = line.split("\\|");
                String sidoNumber = items[8].substring(0, 2);
                String sigunguNumber = items[8].substring(0, 5);
                String roadNumber = items[8].substring(5, 12);

                if(count == 0) {
                    sido = em.find(SiDo.class, items[1]);
                    if(sido == null) {
                        sido = new SiDo();
                        sido.setSidoNumber(sidoNumber);
                        sido.setSidoName(items[1]);
                        sido.setSidoTableName("table_name");
                        sido = (SiDo) merge(em, sido);
                    }
                }

                SiGunGu siGunGu = em.find(SiGunGu.class, sigunguNumber);
                if(siGunGu == null) {
                    siGunGu = new SiGunGu();
                    siGunGu.setSiGunGuNumber(sigunguNumber);
                    siGunGu.setSido(sido);
                    siGunGu.setSiGunGuName(items[2]);
                    siGunGu = (SiGunGu) merge(em, siGunGu);
                }

                Road road = em.find(Road.class, roadNumber);
                if(road == null) {
                    road = new Road();
                    road.setRoadNumber(roadNumber);
                    road.setRoadName(items[9]);
                    road = (Road) merge(em, road);
                }

                TypedQuery<EupMyeonDongRi> query = em.createQuery("select e from EupMyeonDongRi e where e.beopJungRiName=:beopJungRiName and e.beopJungEupMyeonDongName=:beopJungEupMyeonDongName and e.haengJungDongName=:haengJungDongName", EupMyeonDongRi.class);
                query.setParameter("beopJungRiName", items[4]);
                query.setParameter("beopJungEupMyeonDongName", items[3]);
                query.setParameter("haengJungDongName", items[18]);
                EupMyeonDongRi eupMyeonDongRi;
                final List<EupMyeonDongRi> resultList = query.getResultList();
                if(resultList.size() == 1) {
                    eupMyeonDongRi = resultList.get(0);
                } else if(resultList.size() == 0) {
                    eupMyeonDongRi = null;
                } else {
                    throw new IllegalArgumentException("EupMyeonDongRi is duplicated");
                }
                if(eupMyeonDongRi == null) {
                    eupMyeonDongRi = new EupMyeonDongRi();
                    eupMyeonDongRi.setSiGunGu(siGunGu);
                    eupMyeonDongRi.setBeopJungEupMyeonDongName(items[3]);
                    eupMyeonDongRi.setBeopJungRiName(items[4]);
                    eupMyeonDongRi.setHaengJungDongName(items[18]);
                    merge(em, eupMyeonDongRi);
                }
                count++;
                if((count % SESSION_FLUSH_ITEM_COUNT) == 0) {
                    em.clear();
                }
                if((count % CONSOLE_DISPLAY_PER_COUNT) == 0) {
                    System.out.print(".");
                }
            }
            em.clear();
            em.close();

            bf.close();
            System.out.println("");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertAddressFromFile(String fileFullName, String encoding) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(fileFullName), encoding));
            String line;
            int count = 0;
            SiDo sido = null;
            SiGunGu siGunGu = null;
            Road road = null;
            EupMyeonDongRi eup = null;
            String sidoNumber = null;

            EntityManager em = emf.createEntityManager();
            List<BaseAddress> addresses = new ArrayList<>();
            while((line = bf.readLine()) != null) {
                String[] items = line.split("\\|");
                sidoNumber = items[8].substring(0, 2);
                String sigunguNumber = items[8].substring(0, 5);
                String roadNumber = items[8].substring(5, 12);
                String buildingControlNumber = items[15];

                if(count == 0) {
                    sido = em.find(SiDo.class, sidoNumber);
                }
                if(siGunGu == null || !siGunGu.getSiGunGuNumber().equals(sigunguNumber)) {
                    siGunGu = em.find(SiGunGu.class, sigunguNumber);
                }
                if(road == null || !road.getRoadNumber().equals(roadNumber)) {
                    road = em.find(Road.class, roadNumber);
                }
                if(eup == null || !(eup.getBeopJungRiName().equals(items[4]) &&
                        eup.getBeopJungEupMyeonDongName().equals(items[3]) &&
                        eup.getHaengJungDongName().equals(items[18])) ) {
                    TypedQuery<EupMyeonDongRi> query = em.createQuery("select e from EupMyeonDongRi e where e.beopJungRiName=:beopJungRiName and e.beopJungEupMyeonDongName=:beopJungEupMyeonDongName and e.haengJungDongName=:haengJungDongName", EupMyeonDongRi.class);
                    query.setParameter("beopJungRiName", items[4]);
                    query.setParameter("beopJungEupMyeonDongName", items[3]);
                    query.setParameter("haengJungDongName", items[18]);
                    eup = query.getSingleResult();
                }

                if(sido == null || siGunGu == null || road == null || eup == null) {
                    throw new IllegalArgumentException("Base data is not found!!");
                }

                BaseAddress address = getBaseCityInstance(sidoNumber);
                address.setBuildingControlNumber(buildingControlNumber);
                address.setSiGunGu(siGunGu);
                address.setRoad(road);
                address.setEupMyeonDongRi(eup);
                address.setJibeonMainNumber(getIntValue(items[6]));
                address.setJibeonSubNumber(getIntValue(items[7]));
                address.setBuildingMainNumber(getIntValue(items[11]));
                address.setBuildingSubNumber(getIntValue(items[12]));
                address.setBuildingName(items[13]);
                address.setPostCode(items[19]);
                address.setSiGunguBuildingName(items[25]);
                addresses.add(address);
                count++;
                if((count % CONSOLE_DISPLAY_PER_COUNT) == 0) {
                    System.out.print(".");
                }
            }
            bf.close();
            int mergeCount = 0;
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            for(BaseAddress address : addresses) {
                em.merge(address);
                mergeCount++;
                if((mergeCount % CONSOLE_DISPLAY_PER_COUNT) == 0) {
                    System.out.print(".");
                }
                if((mergeCount % SESSION_FLUSH_ITEM_COUNT) == 0) {
                    em.flush();
                    em.clear();
                }
            }
            transaction.commit();
            em.close();
            System.out.println("");
            return true;
        } catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private Integer getIntValue(String item) {
        if(item == null || item.equals("")) {
            return null;
        } else {
            return Integer.parseInt(item);
        }
    }

    private BaseAddress getBaseCityInstance(String sidoNumber) {
        BaseAddress address = null;
        switch (sidoNumber)
        {
            case "11":
                address = new Seoul();
                break;
            case "26":
                address = new Busan();
                break;
            case "27":
                address = new Daegu();
                break;
            case "28":
                address = new Incheon();
                break;
            case "29":
                address = new Gwangju();
                break;
            case "30":
                address = new Daejeon();
                break;
            case "31":
                address = new Ulsan();
                break;
            case "36":
                address = new Sejong();
                break;
            case "41":
                address = new Gyeonggido();
                break;
            case "42":
                address = new Gangwondo();
                break;
            case "43":
                address = new ChungcheongBukdo();
                break;
            case "44":
                address = new ChungcheongNamdo();
                break;
            case "45":
                address = new JeollaBukdo();
                break;
            case "46":
                address = new JeollaNamdo();
                break;
            case "47":
                address = new GyeongsangBukdo();
                break;
            case "48":
                address = new GyeongsangNamdo();
                break;
            case "50":
                address = new Jejudo();
                break;
        }
        return address;
    }
}
