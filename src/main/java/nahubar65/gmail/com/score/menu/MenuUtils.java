package nahubar65.gmail.com.score.menu;

import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.item.DefaultItemClickable;
import team.unnamed.gui.menu.MenuBuilder;

public class MenuUtils {

    public static void fillBorder(MenuBuilder menuBuilder, ItemStack itemStack, int rows) {
        for (int i = 1 ; i <= 7; i++) {
            menuBuilder.addItem(new DefaultItemClickable(i, itemStack, inventoryClickEvent -> true));
        }
        for (int i = 0; i < 9 * rows; i+=9) {
            menuBuilder.addItem(new DefaultItemClickable(i, itemStack, inventoryClickEvent -> true));
        }
        for (int i = 8; i < (9 * rows - 1); i+=9) {
            menuBuilder.addItem(new DefaultItemClickable(i, itemStack, inventoryClickEvent -> true));
        }
        for (int i = (9 * (rows - 1)) ; i < (9 * rows); i++) {
            menuBuilder.addItem(new DefaultItemClickable(i, itemStack, inventoryClickEvent -> true));
        }
    }
}