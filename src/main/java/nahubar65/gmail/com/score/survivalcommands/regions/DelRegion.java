package nahubar65.gmail.com.score.survivalcommands.regions;

import nahubar65.gmail.com.score.caches.UUIDCache;
import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.storages.RegionStorage;
import nahubar65.gmail.com.score.utils.Pair;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class DelRegion extends CommandArgument {
    public DelRegion(Command command, UUIDCache<Pair<Location, Location>> uuidCache, RegionStorage regionStorage) {
        super(command);
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {

    }

    @Override
    public String identifier() {
        return null;
    }

    @Override
    public String usage() {
        return null;
    }

    @Override
    public boolean useOnlyPlayer() {
        return false;
    }

    @Override
    public String getPermission() {
        return null;
    }
}
