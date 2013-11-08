package co.kr.daesung.app.center.domain.constants;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/8/13
 * Time: 12:34 PM
 * Sido Number constants
 */
public enum SidoEnum {
    SEOUL(11),
    BUSAN(26),
    DAEGU(27),
    INCHEON(28),
    GWANGJU(29),
    DAEJEON(30),
    ULSAN(31),
    SEJONG(36),
    GYEONGGIDO(41),
    GANGWONDO(42),
    CHUNGCHEONGBUKDO(43),
    CHUNGCHEONGNAMDO(44),
    JEOLLABUKDO(45),
    JEOLLONAMDO(46),
    GYEONGSANGBUKDO(47),
    GYEONGSANGNAMDO(48),
    JEJUDO(50);

    private int value;

    private SidoEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SidoEnum getEnum(String sidoNumber) {
        Integer intValue = Integer.parseInt(sidoNumber);
        switch(intValue) {
            case 11:
                return SEOUL;
            case 26:
                return BUSAN;
            case 27:
                return DAEGU;
            case 28:
                return INCHEON;
            case 29:
                return GWANGJU;
            case 30:
                return DAEJEON;
            case 31:
                return ULSAN;
            case 36:
                return SEJONG;
            case 41:
                return GYEONGGIDO;
            case 42:
                return GANGWONDO;
            case 43:
                return CHUNGCHEONGBUKDO;
            case 44:
                return CHUNGCHEONGNAMDO;
            case 45:
                return JEOLLABUKDO;
            case 46:
                return JEOLLONAMDO;
            case 47:
                return GYEONGSANGBUKDO;
            case 48:
                return GYEONGSANGNAMDO;
            case 50:
            default:
                return JEJUDO;
        }
    }
}
