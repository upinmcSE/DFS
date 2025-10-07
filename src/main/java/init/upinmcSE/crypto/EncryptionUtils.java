package init.upinmcSE.crypto;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.EOFException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

public class EncryptionUtils {
    private static final SecureRandom RAND = new SecureRandom();

    public static void copyEncrypt(byte[] key, InputStream src, OutputStream dst) throws Exception {
        SecretKeySpec skey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");

        byte[] iv = new byte[16];
        RAND.nextBytes(iv);
        dst.write(iv); // prepend IV
        cipher.init(Cipher.ENCRYPT_MODE, skey, new IvParameterSpec(iv));

        try (CipherOutputStream cos = new CipherOutputStream(dst, cipher)) {
            byte[] buffer = new byte[32 * 1024];
            int n;
            while ((n = src.read(buffer)) != -1) {
                cos.write(buffer, 0, n);
            }
            cos.flush();
        }
    }

    public static void copyDecrypt(byte[] key, InputStream src, OutputStream dst) throws Exception {
        byte[] iv = new byte[16];
        int read = src.read(iv);
        if (read != iv.length) throw new EOFException("Cannot read IV");
        SecretKeySpec skey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, skey, new IvParameterSpec(iv));

        try (CipherInputStream cis = new CipherInputStream(src, cipher)) {
            byte[] buffer = new byte[32 * 1024];
            int n;
            while ((n = cis.read(buffer)) != -1) {
                dst.write(buffer, 0, n);
            }
            dst.flush();
        }
    }

    public static void decryptToStream(byte[] key, InputStream in, OutputStream out) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        try (CipherInputStream cis = new CipherInputStream(in, cipher)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
