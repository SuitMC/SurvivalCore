package nahubar65.gmail.com.score.caches;

import java.util.Map;

public interface Cache<K, V> {

    Map<K, V> get();

    default V find(K key){
        return get().get(key);
    }

    default boolean exists(K key){
        return get().containsKey(key);
    }

    default void add(K key, V value){
        get().put(key, value);
    }

    default void remove(K key){
        get().remove(key, find(key));
    }
}
