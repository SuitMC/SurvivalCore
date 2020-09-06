package nahubar65.gmail.com.score.survivalcommands.warps;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.storages.RegionStorage;
import nahubar65.gmail.com.score.storages.WarpStorage;
import nahubar65.gmail.com.score.utils.Region;
import nahubar65.gmail.com.score.utils.Warp;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Optional;

public class SetRegion extends CommandArgument {

    private RegionStorage regionStorage;

    private WarpStorage warpStorage;

    public SetRegion(Command command, RegionStorage regionStorage, WarpStorage warpStorage) {
        super(command);
        this.regionStorage = regionStorage;
        this.warpStorage = warpStorage;
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        if (args.length > 1) {
            String arg1 = args[0];
            String arg2 = args[1];
            Optional<Region> regionOptional = regionStorage.find(arg1);
            Optional<Warp> warpOptional = warpStorage.find(arg2);
            if (regionOptional.isPresent() && warpOptional.isPresent()) {
                Warp warp = warpOptional.get();
                Region region = regionOptional.get();
                warp.setRegion(region);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSe le ha atribuido al warp &9"+warp.getName()+" &ala region &B"+region.getName()+"&a."));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUso incorrecto! "+usage()));
            }
        }else{
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUso incorrecto! "+usage()));
        }
    }

    @Override
    public String identifier() {
        return "setregion";
    }

    @Override
    public String usage() {
        return "&aUso: &b/warpmanager setregion &e<Warp> &c<Region>";
    }

    @Override
    public boolean useOnlyPlayer() {
        return true;
    }

    @Override
    public String getPermission() {
        return "score.admin.*";
    }
}
