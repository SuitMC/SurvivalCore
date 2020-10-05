package nahubar65.gmail.com.score.menu;

import nahubar65.gmail.com.score.confirmableaction.ConfirmableAction;
import nahubar65.gmail.com.score.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.api.item.ItemClickable;
import team.unnamed.gui.item.DefaultItemClickable;
import team.unnamed.gui.menu.MenuBuilder;

public class ConfirmableActionMenu {

    public static MenuBuilder getBuilder(int rows, ConfirmableAction confirmableAction) {
        MenuBuilder menuBuilder = MenuBuilder.newBuilder("Confirmar?");
        ItemClickable green = new DefaultItemClickable(30,
                ItemUtils.setName(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5), ""),
                inventoryClickEvent -> {
            confirmableAction.onConfirm();
            inventoryClickEvent.getWhoClicked().closeInventory();
            return true;
        });
        ItemClickable red = new DefaultItemClickable(32,
                ItemUtils.setName(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14), ""),
                inventoryClickEvent -> {
                    inventoryClickEvent.getWhoClicked().closeInventory();
                    return true;
                });
        menuBuilder.addItem(green);
        menuBuilder.addItem(red);
        MenuUtils.fillBorder(menuBuilder, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15),
                rows);
        return menuBuilder;
    }
}