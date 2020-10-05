package nahubar65.gmail.com.score.events;

import nahubar65.gmail.com.score.regions.Region;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RegionLeftEvent extends Event implements Cancellable {

    private boolean canceled;

    private static HandlerList handlerList = new HandlerList();

    private Player player;

    private Region region;

    public RegionLeftEvent(Player player, Region region) {
        this.player = player;
        this.region = region;
    }

    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.canceled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Player getPlayer() {
        return player;
    }

    public Region getRegion() {
        return region;
    }
}