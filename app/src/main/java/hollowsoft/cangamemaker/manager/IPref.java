package hollowsoft.cangamemaker.manager;

public interface IPref {

    <T> T get(String key);

    <T> boolean put(String key, T entity);

    boolean remove(String key);
}
