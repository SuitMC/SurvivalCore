package nahubar65.gmail.com.score.plots;

import nahubar65.gmail.com.score.SurvivalCore;
import nahubar65.gmail.com.score.caches.UUIDCache;
import nahubar65.gmail.com.score.confirmableaction.ConfirmableActionTask;
import nahubar65.gmail.com.score.decorator.TextDecorator;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DefaultPlotRequestSender implements PlotRequestSender {

    private UUIDCache<ConfirmableActionTask> uuidCache;

    private PlotRegion plotRegion;

    public DefaultPlotRequestSender(PlotRegion plotRegion) {
        this.uuidCache = new UUIDCache<>();
        this.plotRegion = plotRegion;
    }

    @Override
    public UUIDCache<ConfirmableActionTask> requestQueue() {
        return uuidCache;
    }

    @Override
    public boolean accept(UUID uuid) {
        ConfirmableActionTask confirmableActionTask = uuidCache.find(uuid);
        if (confirmableActionTask != null) {
            confirmableActionTask.confirm();
        }
        return confirmableActionTask != null;
    }

    @Override
    public void sendRequest(Player player, Player recruiter) {
        MemberManager memberManager = plotRegion.getMemberManager();
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(memberManager.getOwner());
        ConfirmableActionTask confirmableActionTask = new ConfirmableActionTask() {
            @Override
            public void onConfirm() {
                player.sendMessage(TextDecorator.color("&aAhora formas parte de &b" + plotRegion.getRegion().getName() + "&a."));
                for (UUID uuid : memberManager.all()) {
                    Player player1 = Bukkit.getPlayer(uuid);
                    if (player1 != null)
                        player1.sendMessage(TextDecorator.color("&9" + offlinePlayer.getName() + " &aahora forma parte de &b" + plotRegion.getRegion().getName() + "&a."));
                }
                memberManager.makeMember(player.getUniqueId());
                requestQueue().remove(player.getUniqueId());
            }

            @Override
            public void orElse() {
                player.sendMessage(TextDecorator.color("&7&m--------------------------"));
                player.sendMessage(TextDecorator.color("&eLa invitación para unirte a &b" + plotRegion.getRegion().getName() + " &ede &a" + offlinePlayer.getName()));
                player.sendMessage(TextDecorator.color("&eha expirado."));
                player.sendMessage(TextDecorator.color("&7&m--------------------------"));
                requestQueue().remove(player.getUniqueId());
            }
        };
        confirmableActionTask.start(30, SurvivalCore.plugin());
        requestQueue().add(player.getUniqueId(), confirmableActionTask);
        player.sendMessage(TextDecorator.color("&7&m--------------------------"));
        player.sendMessage(TextDecorator.color("&b" + recruiter.getName() + "&a le esta invitando a usted a formar parte de &e" + plotRegion.getRegion().getName() + "&a."));
        player.sendMessage(TextDecorator.color("&aTienes &930 &asegundos para aceptar."));
        TextComponent textComponent = new TextComponent(TextDecorator.color("&6Click aqui para aceptar la invitación"));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/parcelas join " + plotRegion.getRegion().getName()));
        player.spigot().sendMessage(textComponent);
        player.sendMessage(TextDecorator.color("&7&m--------------------------"));
    }
}