package nahubar65.gmail.com.score.menu;

import org.bukkit.inventory.Inventory;

import java.util.Map;

public interface Menu {

    Inventory getInventory(Map<String, Object> args) throws ClassCastException;

}