package nahubar65.gmail.com.score.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import team.unnamed.gui.item.lore.LoreBuilder;

import java.util.List;

public class ItemUtils {

    public static ItemStack setName(ItemStack itemStack, String name){
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
        return new ItemStack(itemStack);
    }

    public static ItemStack setLore(ItemStack itemStack, List<String> stringList){
        ItemMeta meta = itemStack.getItemMeta();
        LoreBuilder loreBuilder = LoreBuilder.newBuilder(stringList);
        meta.setLore(loreBuilder.colorize().build());
        itemStack.setItemMeta(meta);
        return new ItemStack(itemStack);
    }

    public static ItemStack setLore(ItemStack itemStack, String... lines){
        return setLore(itemStack, Utils.fromArray(lines));
    }
}