package init.upinmcSE.crypto;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HexFormat;

public class HashUtils {
    private static final SecureRandom RAND = new SecureRandom();

    public static String generateID() {
        byte[] buf = new byte[32];
        RAND.nextBytes(buf);
        return HexFormat.of().formatHex(buf);
    }

    public static String hashMD5(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] d = md.digest(key.getBytes("UTF-8"));
            return HexFormat.of().formatHex(d);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] newEncryptionKey() {
        byte[] key = new byte[32];
        RAND.nextBytes(key);
        return key;
    }
}
