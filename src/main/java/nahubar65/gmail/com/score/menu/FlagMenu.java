package nahubar65.gmail.com.score.menu;

import dev.dbassett.skullcreator.SkullCreator;
import nahubar65.gmail.com.score.decorator.TextDecorator;
import nahubar65.gmail.com.score.plots.MemberManager;
import nahubar65.gmail.com.score.plots.PlotRegion;
import nahubar65.gmail.com.score.regions.Region;
import nahubar65.gmail.com.score.regions.RegionFlag;
import nahubar65.gmail.com.score.sounds.Sounds;
import nahubar65.gmail.com.score.utils.ItemUtils;
import nahubar65.gmail.com.score.utils.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import team.unnamed.gui.item.DefaultItemClickable;
import team.unnamed.gui.item.lore.LoreBuilder;
import team.unnamed.gui.menu.MenuBuilder;

import java.util.Map;

public class FlagMenu implements Menu {

    private final ItemStack redSkull;

    private final ItemStack greenSkull;

    public FlagMenu(){
        redSkull = SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/25ef68dcbd58234ba7aee2ad91ca6fa7ce23f9a32345b48d6e5f5b86a68b5b");
        greenSkull = SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/921928ea67d3a8b97d212758f15cccac1024295b185b319264844f4c5e1e61e");
    }

    @Override
    public Inventory getInventory(Map<String, Object> args) {
        Player player = (Player) args.get("player");
        PlotRegion region = (PlotRegion) args.get("region");
        MemberManager memberManager = region.getMemberManager();
        MenuBuilder menuBuilder = MenuBuilder.newBuilder(TextDecorator.color("&3Region: &9"+region.getRegion().getName()));
        menuBuilder.cancelClick(true);
        int j = 0;
        for (int i = 0; i < RegionFlag.values().length; i++) {
            j = i;
            RegionFlag regionFlag = RegionFlag.values()[i];
            Pair<RegionFlag, Boolean> flagBooleanPair = region.getRegion().getFlag(regionFlag);
            if (!player.isOp() && regionFlag.isRequiredOp()) {
                continue;
            }
            ItemStack finalSkull = switchSkull(flagBooleanPair);
            menuBuilder.addItem(new DefaultItemClickable(i, finalSkull, inventoryClickEvent -> {
                if (memberManager.isSubOwner(player.getUniqueId())) {
                    Pair<RegionFlag, Boolean> regionFlagBooleanPair = region.getRegion().getFlag(regionFlag);
                    region.getRegion().addFlag(regionFlag, regionFlagBooleanPair.getValue() ? false : true);
                    regionFlagBooleanPair = region.getRegion().getFlag(regionFlag);
                    if (regionFlagBooleanPair.getValue()) {
                        Sounds.LEVEL_UP.play(player, 2, 2);
                    } else {
                        Sounds.VILLAGER_NO.play(player, 2, 2);
                    }
                    inventoryClickEvent.setCurrentItem(switchSkull(regionFlagBooleanPair));
                }
                return true;
            }));
        }
        Inventory inventory = menuBuilder.build();
        for (int i = j; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15));
        }
        return inventory;
    }

    private ItemStack switchSkull(Pair<RegionFlag, Boolean> flagBooleanPair){
        ItemStack finalSkull = flagBooleanPair.getValue() ? greenSkull : redSkull;
        finalSkull = ItemUtils.setName(finalSkull, TextDecorator.color("&7» &e"+flagBooleanPair.getKey().getName()));
        ItemMeta meta = finalSkull.getItemMeta();
        LoreBuilder loreBuilder = LoreBuilder.newBuilder();
        String state = flagBooleanPair.getValue() ? "&aActivo" : "&cInactivo";
        loreBuilder.addLines("&bEstado: "+state);
        meta.setLore(loreBuilder.colorize().build());
        finalSkull.setItemMeta(meta);
        return finalSkull;
    }
}