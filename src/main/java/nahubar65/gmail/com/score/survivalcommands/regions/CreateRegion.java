package nahubar65.gmail.com.score.survivalcommands.regions;

import nahubar65.gmail.com.score.caches.UUIDCache;
import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.storages.RegionStorage;
import nahubar65.gmail.com.score.utils.Pair;
import nahubar65.gmail.com.score.regions.Region;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateRegion extends CommandArgument {

    private UUIDCache<Pair<Location, Location>> uuidCache;

    private RegionStorage regionStorage;

    public CreateRegion(Command command, UUIDCache<Pair<Location, Location>> uuidCache, RegionStorage regionStorage) {
        super(command);
        this.uuidCache = uuidCache;
        this.regionStorage = regionStorage;
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        Player player = (Player) sender;
        String arg1 = args[0];

        Pair<Location, Location> pair = uuidCache.find(player.getUniqueId());

        regionStorage.add(arg1, new Region(arg1, pair.getKey(), pair.getValue()));
        sender.sendMessage("&a¡Region &9"+arg1+" &acreada!");
        uuidCache.remove(player.getUniqueId());
    }

    @Override
    public String identifier() {
        return "create";
    }

    @Override
    public String usage() {
        return "/region create";
    }

    @Override
    public boolean useOnlyPlayer() {
        return false;
    }

    @Override
    public boolean canExecute(CommandSender commandSender) {
        Player player = (Player) commandSender;

        Pair<Location, Location> pair = uuidCache.find(player.getUniqueId());

        return pair.getKey() == null || pair.getValue() == null;
    }

    @Override
    public void ifCantExecute(CommandSender commandSender) {
        commandSender.sendMessage("&c¡Una de las dos selecciones es nula!");
    }
}