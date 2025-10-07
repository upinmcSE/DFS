package init.upinmcSE.core;

import init.upinmcSE.crypto.EncryptionUtils;
import init.upinmcSE.p2p.Constants;
import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;

@Setter
@Getter
public class Store {
    private String root;
    private PathTransformFunc pathTransformFunc;

    public Store(String root, PathTransformFunc pathTransformFunc) {
        this.root = (root == null || root.isEmpty()) ? Constants.defaultRootFolderName : root;
        this.pathTransformFunc = pathTransformFunc != null ? pathTransformFunc : key -> new PathKey(key, key);
    }

    public boolean has(String id, String key) {
        PathKey pk = pathTransformFunc.transform(key);
        Path full = Paths.get(root, id, pk.getPathName());
        return Files.exists(full);
    }

    public void clear() throws IOException {
        Path r = Paths.get(root);
//        if (Files.exists(r)) {
//            try { Files.walk(r).map(Path::toFile).forEach(File::delete); }
//            catch (Exception e) { throw new IOException(e); }
//        }
    }

    public void delete(String id, String key) throws IOException {
        PathKey pk = pathTransformFunc.transform(key);
        Path first = Paths.get(root, id, pk.firstPathName());
//        if (Files.exists(first)) {
//            Files.walk(first).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
//        }
    }

    public long write(String id, String key, InputStream r) throws IOException {
        try (OutputStream os = openFileForWriting(id, key)) {
            return copyStream(r, os);
        }
    }

    public long writeDecrypt(byte[] encKey, String id, String key, InputStream r) throws Exception {
        try (OutputStream os = openFileForWriting(id, key)) {
            EncryptionUtils.decryptToStream(encKey, r, os);
            return 0L;
        }
    }

    public ReadResult read(String id, String key) throws IOException {
        PathKey pk = pathTransformFunc.transform(key);
        Path full = Paths.get(root, id, pk.fullPath());
        if (!Files.exists(full)) throw new FileNotFoundException(full.toString());
        long size = Files.size(full);
        InputStream is = Files.newInputStream(full, StandardOpenOption.READ);
        return new ReadResult(size, is);
    }

    private OutputStream openFileForWriting(String id, String key) throws IOException {
        PathKey pk = pathTransformFunc.transform(key);
        Path dir = Paths.get(root, id, pk.getPathName());
        Files.createDirectories(dir);
        Path full = dir.resolve(pk.getFilename());
        return Files.newOutputStream(full, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private long copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[32 * 1024];
        int n;
        long total = 0;
        while ((n = in.read(buf)) != -1) {
            out.write(buf, 0, n);
            total += n;
        }
        out.flush();
        return total;
    }

    public static class ReadResult {
        public final long size;
        public final InputStream input;
        public ReadResult(long size, InputStream input) { this.size = size; this.input = input; }
    }
}
