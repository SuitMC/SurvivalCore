package nahubar65.gmail.com.score.caches;

import java.util.HashMap;
import java.util.Map;

public class SimpleCacheImpl<K, V> implements Cache<K, V>{

    private Map<K, V> map;

    public SimpleCacheImpl() {
        this.map = new HashMap<>();
    }

    @Override
    public Map<K, V> get() {
        return map;
    }
}