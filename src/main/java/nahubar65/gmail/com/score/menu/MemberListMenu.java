package nahubar65.gmail.com.score.menu;

import dev.dbassett.skullcreator.SkullCreator;
import nahubar65.gmail.com.score.confirmableaction.ConfirmableAction;
import nahubar65.gmail.com.score.decorator.TextDecorator;
import nahubar65.gmail.com.score.level.Rank;
import nahubar65.gmail.com.score.level.RankText;
import nahubar65.gmail.com.score.plots.MemberManager;
import nahubar65.gmail.com.score.plots.PlotRegion;
import nahubar65.gmail.com.score.sounds.Sounds;
import nahubar65.gmail.com.score.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.item.DefaultItemClickable;
import team.unnamed.gui.menu.MenuBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MemberListMenu implements Menu {

    private final ConfirmableActionMenu confirmableActionMenu = new ConfirmableActionMenu();

    @Override
    public Inventory getInventory(Map<String, Object> args) throws ClassCastException {
        MemberManager memberManager = ((PlotRegion) args.get("region")).getMemberManager();
        Player player = (Player) args.get("player");
        MenuBuilder menuBuilder = MenuBuilder.newBuilder(TextDecorator.color("&eLista de miembros"));
        menuBuilder.cancelClick(true);
        List<UUID> all = memberManager.all();
        for (int i = 0; i < all.size(); i++) {
            UUID memberUUID = all.get(i);
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(memberUUID);
            if (offlinePlayer != null) {
                RankText rankText = rank(memberManager, offlinePlayer.getUniqueId());
                Rank rank = player.hasPermission("score.membermanager.bypass") ? rankText.getMajor() : rank(memberManager, player.getUniqueId());
                ItemStack skull = SkullCreator.itemFromName(offlinePlayer.getName());
                skull = ItemUtils.setName(skull, TextDecorator.color("&7» &e"+offlinePlayer.getName()));
                List<String> stringList = new ArrayList<>();
                stringList.add("&7&m---------------");
                stringList.add(rankText.getPrefix());
                stringList.add("&7&m---------------");
                MenuBuilder menuBuilder1 = null;
                skull = ItemUtils.setLore(skull, stringList);
                if(rank.major(rankText) && !player.getUniqueId().equals(offlinePlayer.getUniqueId())) {
                    menuBuilder1 = confirmableActionMenu.getBuilder(6, new ConfirmableAction() {
                        @Override
                        public void onConfirm() {
                            memberManager.removeMember(offlinePlayer.getUniqueId());
                            Sounds.CLICK.play(player, 2, 1);
                            player.closeInventory();
                        }

                        @Override
                        public void orElse() {

                        }
                    });
                    stringList.add("");
                    stringList.add(TextDecorator.color("&cClick para expulsar"));
                    skull = ItemUtils.setLore(skull, stringList);
                }
                MenuBuilder finalMenuBuilder = menuBuilder1;
                menuBuilder.addItem(new DefaultItemClickable(i, skull, inventoryClickEvent -> {
                    if (finalMenuBuilder != null) {
                        player.openInventory(finalMenuBuilder.build());
                    }
                    return true;
                }));
            }
        }
        return menuBuilder.build();
    }

    private RankText rank(MemberManager memberManager, UUID uuid) {
        RankText rank;
        if (memberManager.isOwner(uuid)) {
            rank = new RankText(TextDecorator.color("&cPropietario"), 3);
        } else if (memberManager.isSubOwner(uuid)) {
            rank = new RankText(TextDecorator.color("&eSub-Propietario"), 2);
        } else {
            rank = new RankText(TextDecorator.color("&aMiembro"), 1);
        }
        return rank;
    }
}