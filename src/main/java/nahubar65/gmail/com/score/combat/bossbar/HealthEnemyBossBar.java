package nahubar65.gmail.com.score.combat.bossbar;

import nahubar65.gmail.com.score.vault.VaultHook;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;

import java.util.Optional;
import java.util.UUID;

public class HealthEnemyBossBar {

    private Long cooldown;

    private UUID player;

    private UUID enemy;

    private BossBar bossBar;

    private VaultHook vaultHook;

    public HealthEnemyBossBar(Player player, Player enemy){
        this.player = player.getUniqueId();
        this.enemy = enemy.getUniqueId();
        this.vaultHook = VaultHook.getInstance();
        createBossBar();
        setCooldown(10);
    }

    public boolean update() {
        Player enemy = enemy();
        if (bossBar == null) {
            createBossBar();
        }
        if (enemy != null) {
            float health = (float) (enemy.getHealth() / 20);
            bossBar.setProgress(health);
            setName();
        }

        int seconds = toSeconds();
        return seconds >= 0 && (enemy != null && player() != null);
    }

    public void setCooldown(int seconds) {
        this.cooldown = System.currentTimeMillis() + (seconds * 1000);
    }

    private void createBossBar(){
        Player enemy = enemy();
        Player player = player();
        if (player != null && enemy != null) {
            this.bossBar = BossBarAPI.addBar(player,
                    new TextComponent(ChatColor.translateAlternateColorCodes('&', enemy.getName())),
                    BossBarAPI.Color.PURPLE,
                    BossBarAPI.Style.NOTCHED_20,
                    0f,
                    BossBarAPI.Property.DARKEN_SKY);
        }
    }

    private void setName(){
        if (this.bossBar == null) {
            createBossBar();
            return;
        }
        Player enemy = enemy();
        Optional<Chat> optionalChat = vaultHook.getChat();
        String playerName = enemy.getName();
        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            playerName = (chat.getPlayerPrefix(enemy) + " " + playerName + " " + chat.getPlayerSuffix(enemy));
        }
        if (playerName.length() > 64) {
            playerName = playerName.substring(0, 64);
        }
        bossBar.setMessage(playerName);
    }

    public Player enemy(){
        return Bukkit.getPlayer(enemy);
    }

    public Player player(){
        return Bukkit.getPlayer(player);
    }

    private int toSeconds(){
        return Math.toIntExact(Math.round((cooldown - System.currentTimeMillis()) / 1000));
    }
}