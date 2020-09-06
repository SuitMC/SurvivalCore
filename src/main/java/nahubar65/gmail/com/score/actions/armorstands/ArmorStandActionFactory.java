package nahubar65.gmail.com.score.actions.armorstands;

import nahubar65.gmail.com.score.actions.AbstractAction;
import nahubar65.gmail.com.score.actions.AbstractActionCache;
import org.bukkit.entity.ArmorStand;

public class ArmorStandActionFactory {

    private final static AbstractActionCache ABSTRACT_ACTION_CACHE = new AbstractActionCache();

    public static ArmorStand setAction(ArmorStand armorStand, AbstractAction abstractAction){
        ABSTRACT_ACTION_CACHE.add(armorStand.getUniqueId(), abstractAction);
        return armorStand;
    }

    public static AbstractAction getAction(ArmorStand armorStand){
        return ABSTRACT_ACTION_CACHE.find(armorStand.getUniqueId());
    }

    public static void removeAction(ArmorStand armorStand){
        ABSTRACT_ACTION_CACHE.remove(armorStand.getUniqueId());
    }
}