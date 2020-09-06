package nahubar65.gmail.com.score.survivalcommands.regions;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.storages.RegionStorage;
import nahubar65.gmail.com.score.utils.Region;
import nahubar65.gmail.com.score.utils.RegionFlag;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Optional;

public class AddFlag extends CommandArgument {

    private RegionStorage regionStorage;

    public AddFlag(Command command, RegionStorage regionStorage) {
        super(command);
        this.regionStorage = regionStorage;
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        if (args.length > 2) {
            String arg1 = args[0];
            Optional<Region> regionOptional = regionStorage.find(arg1);
            if (regionOptional.isPresent()) {
                String arg2 = args[1];
                String arg3 = args[2];
                Region region = regionOptional.get();
                try {
                    RegionFlag regionFlag = RegionFlag.valueOf(arg2);
                    Boolean b = arg3.equalsIgnoreCase("true");
                    region.addFlag(regionFlag, b);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aFlag &9"+regionFlag.name()+" &aañadido."));
                    return;
                }catch (Exception e){

                }
            }
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUso incorrecto! "+usage()));
    }

    @Override
    public String identifier() {
        return "addflag";
    }

    @Override
    public String usage() {
        return "&aUso: &b/region addflag &e<Region> &c<Flag> &9<false/true>";
    }

    @Override
    public boolean useOnlyPlayer() {
        return false;
    }

    @Override
    public String getPermission() {
        return "score.region.addflag";
    }
}