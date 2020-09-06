package nahubar65.gmail.com.score.actions;

import nahubar65.gmail.com.score.caches.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AbstractActionCache implements Cache<UUID, AbstractAction> {

    private final HashMap<UUID, AbstractAction> actionHashMap = new HashMap<>();

    @Override
    public Map<UUID, AbstractAction> get() {
        return actionHashMap;
    }
}
