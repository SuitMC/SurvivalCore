package nahubar65.gmail.com.score.survivalcommands.regions;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.storages.RegionStorage;
import nahubar65.gmail.com.score.regions.Region;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.text.NumberFormat;

public class RegionList extends CommandArgument {

    private RegionStorage regionStorage;

    public RegionList(Command command, RegionStorage regionStorage) {
        super(command);
        this.regionStorage = regionStorage;
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        for (Region region : regionStorage.get().values()) {
            String format = "<Name> &b- &a1: <firstBound> &a2: <secondBound>";
            format = format.replace("<firstBound>", locationToString(region.getMin()));
            format = format.replace("<secondBound>", locationToString(region.getMax()));
            format = format.replace("<Name>", region.getName());
            sender.sendMessage(color(format));
        }
    }

    @Override
    public String identifier() {
        return "list";
    }

    @Override
    public String usage() {
        return "/region list";
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