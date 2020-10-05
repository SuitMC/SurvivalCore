package nahubar65.gmail.com.score.menu;

import nahubar65.gmail.com.score.SurvivalCore;
import nahubar65.gmail.com.score.caches.UUIDCache;
import nahubar65.gmail.com.score.cooldown.Cooldown;
import nahubar65.gmail.com.score.decorator.TextDecorator;
import nahubar65.gmail.com.score.plots.MemberManager;
import nahubar65.gmail.com.score.plots.PlotRegion;
import nahubar65.gmail.com.score.prompt.MemberPrompt;
import nahubar65.gmail.com.score.prompt.OwnerPrompt;
import nahubar65.gmail.com.score.prompt.SubOwnerPrompt;
import nahubar65.gmail.com.score.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.item.DefaultItemClickable;
import team.unnamed.gui.menu.MenuBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemberManagerMenu implements Menu {

    private Menu memberListMenu = new MemberListMenu();

    private final UUIDCache<Cooldown> cooldownUUIDCache = new UUIDCache<>();

    @Override
    public Inventory getInventory(Map<String, Object> args) throws ClassCastException {
        PlotRegion plotRegion = (PlotRegion) args.get("region");
        MemberManager memberManager = plotRegion.getMemberManager();
        Player player = (Player) args.get("player");
        UUID uuid = player.getUniqueId();
        MenuBuilder menuBuilder = MenuBuilder.newBuilder(TextDecorator.color("&aAdministración de miembros"));
        menuBuilder.addItem(new DefaultItemClickable(30, ItemUtils.setName(new ItemStack(Material.EMERALD_BLOCK),
                TextDecorator.color("&aAgregar nuevo miembro")), inventoryClickEvent -> {
            if (memberManager.isSubOwner(uuid) || memberManager.isOwner(uuid) || player.hasPermission("score.plot.bypass"))
                if(!cooldownUUIDCache.exists(uuid) || cooldownUUIDCache.find(uuid).end() || player.hasPermission("score.plot.bypass")) {
                    new MemberPrompt(plotRegion).begin(SurvivalCore.getFactory(), player);
                    cooldownUUIDCache.add(uuid, new Cooldown(20));
                } else {
                    player.sendMessage(TextDecorator.color("&c¡Debes esperar antes de volver a invitar!"));
                }
            else {
                player.sendMessage(TextDecorator.color("&c¡No puedes hacer eso!"));
            }
            return true;
        }));
        menuBuilder.addItem(new DefaultItemClickable(31, ItemUtils.setName(new ItemStack(Material.GOLD_BLOCK),
                TextDecorator.color("&aAsignar &eSub-Propietario")), inventoryClickEvent -> {
            if (memberManager.isOwner(uuid) || player.hasPermission("score.plot.bypass"))
                new SubOwnerPrompt(plotRegion).begin(SurvivalCore.getFactory(), player);
            else {
                player.sendMessage(TextDecorator.color("&c¡No puedes hacer eso!"));
            }
            return true;
        }));
        menuBuilder.addItem(new DefaultItemClickable(32, ItemUtils.setName(new ItemStack(Material.REDSTONE_BLOCK),
                TextDecorator.color("&aAsignar &cPropietario")), inventoryClickEvent -> {
            if (memberManager.isOwner(uuid) || player.hasPermission("score.plot.bypass"))
                new OwnerPrompt(plotRegion).begin(SurvivalCore.getFactory(), player);
            else {
                player.sendMessage(TextDecorator.color("&c¡No puedes hacer eso!"));
            }
            return true;
        }));
        menuBuilder.addItem(new DefaultItemClickable(22, ItemUtils.setName(new ItemStack(Material.DIAMOND),
                TextDecorator.color("&eLista de miembros")), inventoryClickEvent -> {
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("player", player);
            objectMap.put("region", plotRegion);
            player.openInventory(memberListMenu.getInventory(objectMap));
            return true;
        }));
        return menuBuilder.build();
    }
}