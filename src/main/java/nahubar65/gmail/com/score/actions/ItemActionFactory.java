package nahubar65.gmail.com.score.actions;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ItemActionFactory {

    private static final AbstractActionCache ITEM_ACTION_CACHE = new AbstractActionCache();

    public static ItemStack setSpecialItemAction(ItemStack itemStack, AbstractAction specialItemAction){
        NBTItem nbtItem = new NBTItem(itemStack);
        UUID uuid = randomUUID();
        nbtItem.setString("action-uuid", uuid.toString());
        ITEM_ACTION_CACHE.add(uuid, specialItemAction);
        return nbtItem.getItem();
    }

    private static UUID randomUUID(){
        UUID uuid = UUID.randomUUID();
        if (ITEM_ACTION_CACHE.exists(uuid)) {
            return randomUUID();
        }
        return uuid;
    }

    public static AbstractAction getAction(ItemStack itemStack){
        NBTItem nbtItem = new NBTItem(itemStack);
        String s;
        if ((s = nbtItem.getString("action-uuid")) != "") {
            UUID uuid = UUID.fromString(s);
            return ITEM_ACTION_CACHE.find(uuid);
        }
        return null;
    }

    public static void removeAction(ItemStack itemStack){
        NBTItem nbtItem = new NBTItem(itemStack);
        String s;
        if ((s = nbtItem.getString("action-uuid")) != "") {
            UUID uuid = UUID.fromString(s);
            nbtItem.removeKey("action-uuid");
            ITEM_ACTION_CACHE.remove(uuid);
        }
    }
}