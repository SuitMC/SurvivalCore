package nahubar65.gmail.com.score.survivalcommands.warps;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.storages.WarpStorage;
import nahubar65.gmail.com.score.utils.Warp;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.text.NumberFormat;

public class WarpList extends CommandArgument {

    private WarpStorage warpStorage;

    public WarpList(Command command, WarpStorage warpStorage) {
        super(command);
        this.warpStorage = warpStorage;
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        for (Warp value : warpStorage.get().values()) {
            String format = "&5<Name> &b- &e<Spawn> &b- &aRegion: &6<Region>";
            format = format.replace("<Name>", value.getName());
            format = format.replace("<Spawn>", locationToString(value.getSpawnPoint()));
            String regionName = value.getRegion() != null ? value.getRegion().getName() : "";
            format = format.replace("<Region>", regionName);
            sender.sendMessage(color(format));
        }
    }

    @Override
    public String identifier() {
        return "warplist";
    }

    @Override
    public String usage() {
        return "/warpmanager warplist";
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

    private String locationToString(Location location){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(3);
        String locationToString = "&eworld: &b"+location.getWorld().getName()+"," +
                " &ex: &b"+numberFormat.format(location.getX()) +
                " &ey: &b"+numberFormat.format(location.getY()) +
                " &ez: &b"+numberFormat.format(location.getZ());
        return locationToString;
    }
}