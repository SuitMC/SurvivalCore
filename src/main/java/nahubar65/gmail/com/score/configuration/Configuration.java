package nahubar65.gmail.com.score.configuration;

import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.logging.Level;

public class Configuration extends YamlConfiguration {

    private File file;

    private JavaPlugin plugin;

    public Configuration(JavaPlugin plugin, File file) {
        this.file = file;
        this.plugin = plugin;
        createFile();
    }

    private void createFile() {
        if (file == null) {
            return;
        }
        try {
            if (file.exists()) {
                load(file);
                save(file);
                return;
            }

            if (!file.exists()) {
                File folder = new File(file.getParent());
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                file.createNewFile();
            }
            InputStream inputStream;
            if ((inputStream = plugin.getResource(file.getName())) != null) {
                ByteStreams.copy(inputStream, new FileOutputStream(file));
            } else {
                save(file);
            }

            load(file);
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Creation of file " + file.getName() + "failed.");
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            save(file);
            reload();
        } catch (Exception e) {
            throw new RuntimeException("Save of file " + file.getName() + "failed.");
        }
    }

    public void reload() {
        try {
            load(file);
        } catch (Exception e) {
            throw new RuntimeException("Reload of file " + file.getName() + "failed.");
        }
    }
}