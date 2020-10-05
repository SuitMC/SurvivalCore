package nahubar65.gmail.com.score.survivalcommands.regions;

import nahubar65.gmail.com.score.caches.UUIDCache;
import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.storages.RegionStorage;
import nahubar65.gmail.com.score.utils.Pair;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class RegionCommand extends Command {

    private RegionStorage regionStorage;

    public RegionCommand(String command, UUIDCache<Pair<Location, Location>> uuidCache, RegionStorage regionStorage){
        super(command);
        this.regionStorage = regionStorage;
        this.registerNewArgument(new CreateRegion(this, uuidCache, regionStorage));
        this.registerNewArgument(new DelRegion(this, regionStorage));
        this.registerNewArgument(new Wand(this, uuidCache));
        this.registerNewArgument(new AddFlag(this, regionStorage));
        this.registerNewArgument(new FlagList(this));
        this.registerNewArgument(new RegionList(this, regionStorage));
    }

    @Override
    public boolean executeCommand(CommandSender sender, String arg, String[] args) {
        return true;
    }

    @Override
    public boolean useOnlyPlayer() {
        return true;
    }

    @Override
    public List<String> getHelp(CommandSender sender) {
        List<String> help = new ArrayList<>();
        help.add("&a/region setregion <Name> &6-> &9&oCrea una nueva region.");
        help.add("&a/region delregion <Name> &6-> &9&oElimina una region existente.");
        help.add("&a/region wand &6 -> &bObtiene la herramienta de reclamo de tierra.");
        help.add("&a/region addflag <Region> <Flag> <false/true> &6-> &9&oOtorga flags a una region especifica.");
        help.add("&a/region flaglist &6-> &9&oMuestra la lista de flags disponibles.");
        help.add("&a/region list &6-> &9&oMuestra la lista de regiones creadas.");
        return help;
    }

    @Override
    public boolean zeroArguments() {
        return false;
    }
}