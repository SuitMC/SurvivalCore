package nahubar65.gmail.com.score;

import com.fren_gor.packetInjectorAPI.api.PacketEventManager;
import nahubar65.gmail.com.score.events.ServerShutdownEvent;
import nahubar65.gmail.com.score.plugin.PluginDependencyFinder;
import nahubar65.gmail.com.score.services.Service;
import nahubar65.gmail.com.score.services.SurvivalService;
import nahubar65.gmail.com.score.vault.VaultEconomyFinder;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SurvivalCore extends JavaPlugin {

    private Service service;

    private static ConversationFactory factory;

    private PluginDependencyFinder pluginDependencyFinder;

    public void onEnable(){
        this.pluginDependencyFinder = new VaultEconomyFinder() {
            @Override
            public void onFound() {
                service = new SurvivalService(SurvivalCore.this);
                service.start();
            }
        };
        this.pluginDependencyFinder.search(this);
        factory = new ConversationFactory(plugin());
    }

    public void onDisable() {
        Bukkit.getPluginManager().callEvent(new ServerShutdownEvent());
        service.stop();
    }

    public static final JavaPlugin plugin(){
        return JavaPlugin.getPlugin(SurvivalCore.class);
    }

    public static ConversationFactory getFactory() {
        return factory;
    }

    public File getFolder(){
        File folder = getDataFolder();
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }
}