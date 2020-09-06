package nahubar65.gmail.com.score.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerDamageByPlayerEvent extends Event implements Cancellable {

    private boolean cancel;

    private final static HandlerList handlerList = new HandlerList();

    private Player player;

    private Player damager;

    private double damage;

    public PlayerDamageByPlayerEvent(Player player, double damage, Player damager){
        this.damage = damage;
        this.player = player;
        this.damager = damager;
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

    public static HandlerList getHandlerList(){
        return handlerList;
    }

    public Player getPlayer() {
        return player;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public Player getDamager() {
        return damager;
    }
}