package nahubar65.gmail.com.score.listeners;

import nahubar65.gmail.com.score.SurvivalCore;
import nahubar65.gmail.com.score.configuration.Configuration;
import nahubar65.gmail.com.score.storages.PlayerInfoStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.UUID;

public class PlayerListeners implements Listener {

    private PlayerInfoStorage settingsStorage;

    private File folderData;

    public PlayerListeners(PlayerInfoStorage settingsStorage, File folderData){
        this.settingsStorage = settingsStorage;
        this.folderData = folderData;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        settingsStorage.load(new Configuration(SurvivalCore.plugin(), new File(folderData, uuid.toString()+".yml")), uuid);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        settingsStorage.remove(uuid);
    }
}