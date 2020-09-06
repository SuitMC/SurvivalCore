package nahubar65.gmail.com.score.task;

import fr.mrmicky.fastparticle.FastParticle;
import fr.mrmicky.fastparticle.ParticleType;
import nahubar65.gmail.com.score.SurvivalCore;
import nahubar65.gmail.com.score.caches.UUIDCache;
import nahubar65.gmail.com.score.effect.CircleParticleEffect;
import nahubar65.gmail.com.score.nms.NMS;
import nahubar65.gmail.com.score.particle.SimpleParticleModelImpl;
import nahubar65.gmail.com.score.utils.Warp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayerTeleportEffect implements Runnable {

    private Location firstLocation;

    private Location to;

    private int cooldown;

    private Player player;

    private int taskID;

    private UUIDCache<Object> uuidCache;

    private CircleParticleEffect effect;

    private Warp warp;

    public PlayerTeleportEffect(Player player, Warp warp, int cooldown, UUIDCache<Object> uuidCache) {
        this.firstLocation = player.getLocation();
        this.to = warp.getSpawnPoint();
        this.cooldown = cooldown;
        this.warp = warp;
        this.player = player;
        this.uuidCache = uuidCache;
        this.effect = new CircleParticleEffect(player, 1.5, player.getEyeLocation(), new SimpleParticleModelImpl(ParticleType.FIREWORKS_SPARK, 1), 0);
    }

    @Override
    public void run() {
        if (!uuidCache.exists(player.getUniqueId())) {
            new NMS().sendTitle(player, color("&c¡Te has movido!"), color("&3Teletransportación cancelada."), 0, 1, 1);
            Bukkit.getScheduler().cancelTask(taskID);
            this.effect.stop();
            return;
        }
        if (cooldown == 0) {
            player.teleport(to);
            new NMS().sendTitle(player, color("&a"+warp.getName()), color("&e¡Has sido teletransportado!"), 0, 20, 0);
            Bukkit.getScheduler().cancelTask(taskID);
            player.playSound(firstLocation, Sound.LEVEL_UP, 1, 2);
            uuidCache.remove(player.getUniqueId());
            destinationEffect();
            this.effect.stop();
        }else if(cooldown > 0){
            cooldown--;
            new NMS().sendTitle(player, cooldownColor() + cooldown, color("&bSerás teletransportado en:"), 0, 1, 1);
            player.playSound(firstLocation, Sound.CLICK, 1, 1);
        } else {
            Bukkit.getScheduler().cancelTask(taskID);
        }
    }

    public void start(){
        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(SurvivalCore.plugin(), this, 0, 20);
        this.uuidCache.add(player.getUniqueId(), null);
        this.effect.start(01);
    }

    private String cooldownColor(){
        String color = "&5";
        switch (cooldown){
            case 4:
                color = "&a";
                break;
            case 3:
                color = "&e";
                break;
            case 2:
                color = "&6";
                break;
            case 1:
                color = "&c";
                break;
        }
        return color(color);
    }

    private String color(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private void destinationEffect(){
        int radius = 1;
        for (int angle = 0; angle < 60; angle++) {
            double cos = radius * Math.cos(angle);
            double sin = radius * Math.sin(angle);
            FastParticle.spawnParticle(to.getWorld(), ParticleType.FLAME, to.clone().add(cos, 0, sin), 1, 1, 1, 1, 0, 0);
        }
        player.getWorld().playSound(to, Sound.EXPLODE, 1, 1);
    }
}