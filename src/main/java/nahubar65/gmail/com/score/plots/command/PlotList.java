package nahubar65.gmail.com.score.plots.command;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.plots.PlotRegion;
import nahubar65.gmail.com.score.plots.SimplePlotRegion;
import nahubar65.gmail.com.score.storages.SQLPlotStorage;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.NumberFormat;

public class PlotList extends CommandArgument {

    private SQLPlotStorage sqlPlotStorage;

    public PlotList(Command command, SQLPlotStorage sqlPlotStorage) {
        super(command);
        this.sqlPlotStorage = sqlPlotStorage;
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        Player player = (Player) sender;
        sqlPlotStorage.find(player.getUniqueId()).ifPresent(plotRegions -> {
            player.sendMessage(color("&6Lista de parcelas:"));
            for (PlotRegion plotRegion : plotRegions) {
                String format = "<Name> &b- &a1: <firstBound> &a2: <secondBound>";
                format = format.replace("<firstBound>", locationToString(plotRegion.getRegion().getMin()));
                format = format.replace("<secondBound>", locationToString(plotRegion.getRegion().getMax()));
                format = format.replace("<Name>", plotRegion.getRegion().getName());
                player.sendMessage(color(format));
            }
        });
    }

    @Override
    public String identifier() {
        return "list";
    }

    @Override
    public String usage() {
        return "/plot list";
    }

    @Override
    public boolean useOnlyPlayer() {
        return true;
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