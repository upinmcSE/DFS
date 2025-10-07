package init.upinmcSE.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PathKey {
    private String pathName;
    private String filename;

    public PathKey(String pathName, String filename) {
        this.pathName = pathName;
        this.filename = filename;
    }

    public String firstPathName() {
        String[] parts = pathName.split("/");
        if (parts.length == 0) return "";
        return parts[0];
    }

    public String fullPath() {
        return pathName + "/" + filename;
    }
}
