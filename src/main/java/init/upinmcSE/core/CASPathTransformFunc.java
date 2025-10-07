package init.upinmcSE.core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class CASPathTransformFunc implements PathTransformFunc {
    @Override
    public PathKey transform(String key) {
        try {
            // Tính SHA-1 hash của key
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            byte[] hashBytes = sha1.digest(key.getBytes(StandardCharsets.UTF_8));

            // Chuyển sang chuỗi hex
            StringBuilder hashStr = new StringBuilder();
            for (byte b : hashBytes) {
                hashStr.append(String.format("%02x", b));
            }

            // Chia hashStr thành từng block 5 ký tự
            int blockSize = 5;
            int sliceLen = hashStr.length() / blockSize;
            List<String> paths = new ArrayList<>();

            for (int i = 0; i < sliceLen; i++) {
                int from = i * blockSize;
                int to = Math.min(from + blockSize, hashStr.length());
                paths.add(hashStr.substring(from, to));
            }

            // Tạo PathKey tương đương
            String pathName = String.join("/", paths);
            return new PathKey(pathName, hashStr.toString());

        } catch (Exception e) {
            throw new RuntimeException("Failed to transform path: " + e.getMessage(), e);
        }
    }
}
