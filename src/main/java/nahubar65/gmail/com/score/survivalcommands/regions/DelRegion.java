package nahubar65.gmail.com.score.survivalcommands.regions;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.storages.RegionStorage;
import nahubar65.gmail.com.score.regions.Region;
import org.bukkit.command.CommandSender;

import java.util.Optional;

public class DelRegion extends CommandArgument {

    private RegionStorage regionStorage;

    public DelRegion(Command command, RegionStorage regionStorage) {
        super(command);
        this.regionStorage = regionStorage;
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        String regionName = args[0];
        Optional<Region> optionalRegion = regionStorage.find(regionName);
        if (optionalRegion.isPresent()) {
            regionStorage.remove(regionName);
            sender.sendMessage(color("&bRegion &9"+regionName+" &bremovida."));
        } else {
            sender.sendMessage(color("&cEsa region no existe!"));
        }
    }

    @Override
    public String identifier() {
        return "delregion";
    }

    @Override
    public String usage() {
        return " /region delregion <Region>";
    }

    @Override
    public boolean useOnlyPlayer() {
        return false;
    }

    @Override
    public boolean canExecute(CommandSender commandSender) {
        return true;
    }

    @Override
    public void ifCantExecute(CommandSender commandSender) {

    }
}
