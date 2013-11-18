package co.kr.daesung.app.center.api.web.auth;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 5:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ByteUtils {

    public static final Byte DEFAULT_BYTE = Byte.valueOf((byte) 0);
    private static final int RADIUS_VALUE = 16;
    private static final int TEN = 10;
    private static final int OCTA = 8;
    private static final int BINARY = 2;
    private static final int THIRD_STATE = 3;

    public static byte[] toBytes(String digits, int radix) throws IllegalArgumentException {
        if (digits == null) {
            return null;
        }
        if (radix != RADIUS_VALUE && radix != TEN && radix != OCTA) {
            throw new IllegalArgumentException("For input radix: \"" + radix + "\"");
        }
        int divLen = (radix == RADIUS_VALUE) ? BINARY : THIRD_STATE;
        int length = digits.length();
        if (length % divLen == 1) {
            throw new IllegalArgumentException("For input string: \"" + digits + "\"");
        }
        length = length / divLen;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            int index = i * divLen;
            bytes[i] = (byte) (Short.parseShort(digits.substring(index, index + divLen), radix));
        }
        return bytes;
    }

    private static final int UPPER_BYTE = 0xF0;
    private static final int LOWER_BYTE = 0x0F;
    private static final int SHIFT_LENGTH = 4;

    public static String toHexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        StringBuffer result = new StringBuffer();
        for (byte b : bytes) {
            result.append(Integer.toString((b & UPPER_BYTE) >> SHIFT_LENGTH, RADIUS_VALUE));
            result.append(Integer.toString(b & LOWER_BYTE, RADIUS_VALUE));
        }
        return result.toString();
    }
}