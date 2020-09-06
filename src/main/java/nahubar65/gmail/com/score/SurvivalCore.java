package nahubar65.gmail.com.score;

import nahubar65.gmail.com.score.services.Service;
import nahubar65.gmail.com.score.services.SurvivalService;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SurvivalCore extends JavaPlugin {

    private final Service service = new SurvivalService(this);

    public void onEnable(){
        service.start();
    }

    public void onDisable(){
        service.stop();
    }

    public static final JavaPlugin plugin(){
        return JavaPlugin.getPlugin(SurvivalCore.class);
    }

    public File getFolder(){
        File folder = getDataFolder();
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }
}