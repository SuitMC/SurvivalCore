package nahubar65.gmail.com.score.survivalcommands.warps;

import nahubar65.gmail.com.score.SurvivalCore;
import nahubar65.gmail.com.score.caches.UUIDCache;
import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.listeners.TeleportCommandListener;
import nahubar65.gmail.com.score.storages.WarpStorage;
import nahubar65.gmail.com.score.task.PlayerTeleportEffect;
import nahubar65.gmail.com.score.utils.Warp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Spawn extends Command {

    private UUIDCache<Object> uuidCache;

    private JavaPlugin plugin;

    private WarpStorage warpStorage;

    public Spawn(String name, WarpStorage warpStorage, UUIDCache uuidCache, SurvivalCore survivalCore) {
        super(name);
        this.uuidCache = new UUIDCache<>();
        this.warpStorage = warpStorage;
        this.uuidCache = uuidCache;
        this.plugin = survivalCore;
    }

    @Override
    public boolean executeCommand(CommandSender sender, String arg, String[] args) {
        Player player = (Player) sender;
        Optional<Warp> warpOptional = warpStorage.find("spawn");
        if (!warpOptional.isPresent()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[&b!&c] &eEl destino elegido no existe, intente de nuevo más tarde o comuniquese con algún administrador."));
            return true;
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> new PlayerTeleportEffect(player, warpOptional.get(), 10, uuidCache).start());
        return true;
    }

    @Override
    public boolean useOnlyPlayer() {
        return true;
    }

    @Override
    public List<String> getHelp(CommandSender sender) {
        List<String> help = new ArrayList<>();
        return help;
    }

    @Override
    public boolean zeroArguments() {
        return true;
    }
}