package nahubar65.gmail.com.score.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EnderPearlHitEvent extends Event implements Cancellable {

    private boolean cancel;

    private final static HandlerList handlerList = new HandlerList();

    private Location to;

    private Location from;

    private Player player;

    public EnderPearlHitEvent(Player player, Location to, Location from) {
        this.player = player;
        this.to = to;
        this.from = from;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancel = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public final static HandlerList getHandlerList(){
        return handlerList;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getTo() {
        return to;
    }

    public Location getFrom() {
        return from;
    }
}