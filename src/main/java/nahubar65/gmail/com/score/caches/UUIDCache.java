package nahubar65.gmail.com.score.caches;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UUIDCache<T> implements Cache<UUID, T>{

    private Map<UUID, T> uuidMap;

    public UUIDCache(){
        this.uuidMap = new HashMap<>();
    }

    @Override
    public Map<UUID, T> get() {
        return uuidMap;
    }
}