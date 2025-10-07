package init.upinmcSE.core;

@FunctionalInterface
public interface PathTransformFunc {
    PathKey transform(String key);
}
