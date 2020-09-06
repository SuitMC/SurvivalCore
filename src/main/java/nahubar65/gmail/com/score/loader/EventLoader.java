package nahubar65.gmail.com.score.loader;

import nahubar65.gmail.com.score.SurvivalCore;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class EventLoader implements Loadable {

    private JavaPlugin plugin;

    private Listener[] listeners;

    public EventLoader(SurvivalCore survivalCore, Listener... listeners){
        this.listeners = listeners;
        this.plugin = survivalCore;
    }

    @Override
    public void load(){
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        }
    }
}