package co.kr.daesung.app.center.api.web.auth;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/18/13
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringEncrypter {
    private static final int IV_SIZE = 16;
    private static final String DEFAULT_TRANSFORMATION = "AES/ECB/PKCS5Padding";
    public static final String AES = "AES";
    public static final String DES = "DES";
    public static final String TRIPLE_DES = "TripleDES";
    public static final String DES_EDE = "DESede";
    public static final String UTF8 = "UTF-8";

    private StringEncrypter() {

    }

    public static String encrypt(String rawString, String ivKey) throws UnsupportedEncodingException {
        byte[] iv = generateInitVector(ivKey);
        return encrypt(rawString, iv);
    }

    public static String encrypt(String rawString) throws UnsupportedEncodingException {
        byte[] iv = generateInitVector(rawString);
        return encrypt(rawString, iv);
    }

    public static String encrypt(String rawString, byte[] iv) {
        try {
            Key key = generateKey(AES, iv);
            String transformation = DEFAULT_TRANSFORMATION;
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypt = cipher.doFinal(rawString.getBytes(UTF8));
            return ByteUtils.toHexString(encrypt);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public static byte[] generateInitVector(String iv) throws UnsupportedEncodingException {
        byte[] passwordBytes = iv.getBytes(UTF8);
        int len = passwordBytes.length;
        byte[] keyBytes = new byte[IV_SIZE];
        if (len >= IV_SIZE) {
            System.arraycopy(passwordBytes, 0, keyBytes, 0, IV_SIZE);
        } else {
            System.arraycopy(passwordBytes, 0, keyBytes, 0, len);
            for (int i = 0; i < (IV_SIZE - len); i++) {
                keyBytes[len + i] = passwordBytes[i % len];
            }
        }
        return keyBytes;
    }

    public static String decrypt(String hexString, String ivString) throws InvalidKeyException,
            NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, NumberFormatException,
            IllegalBlockSizeException, BadPaddingException, IllegalArgumentException, UnsupportedEncodingException {
        byte[] iv = generateInitVector(ivString);
        Key key = generateKey(AES, iv);
        String transformation = DEFAULT_TRANSFORMATION;
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypt = cipher.doFinal(ByteUtils.toBytes(hexString, IV_SIZE));
        return new String(decrypt);
    }

    public static Key generateKey(String algorithm) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        return keyGenerator.generateKey();
    }

    public static Key generateKey(String algorithm, byte[] keyData) throws NoSuchAlgorithmException,
            InvalidKeyException, InvalidKeySpecException {
        String upper = algorithm.toUpperCase();
        if (DES.equals(upper)) {
            KeySpec keySpec = new DESKeySpec(keyData);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
            return secretKeyFactory.generateSecret(keySpec);
        } else if (DES_EDE.equals(upper) || TRIPLE_DES.equals(upper)) {
            KeySpec keySpec = new DESedeKeySpec(keyData);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
            return secretKeyFactory.generateSecret(keySpec);
        } else {
            return new SecretKeySpec(keyData, algorithm);
        }
    }
}