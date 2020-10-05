package nahubar65.gmail.com.score.plots.command;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.confirmableaction.ConfirmableAction;
import nahubar65.gmail.com.score.confirmableaction.ConfirmableActionTask;
import nahubar65.gmail.com.score.menu.ConfirmableActionMenu;
import nahubar65.gmail.com.score.plots.PlotRegion;
import nahubar65.gmail.com.score.storages.Storage;
import nahubar65.gmail.com.score.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import team.unnamed.gui.menu.MenuBuilder;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class PlotRemove extends CommandArgument {

    private Storage<UUID, List<PlotRegion>> storage;

    public PlotRemove(Command command, Storage<UUID, List<PlotRegion>> storage) {
        super(command);
        this.storage = storage;
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        Player player = (Player) sender;
        if (args.length > 0) {
            String arg1 = args[0];
            PlotRegion plotRegion = Utils.findFromName(arg1, player.getUniqueId(), storage);
            if (plotRegion != null) {
                ConfirmableAction confirmableAction = new ConfirmableActionTask() {
                    @Override
                    public void onConfirm() {
                        List<PlotRegion> plotRegions = storage.find(player.getUniqueId()).get();
                        for (Iterator<PlotRegion> it = plotRegions.iterator(); it.hasNext(); ) {
                            PlotRegion region = it.next();
                            if (plotRegion.equals(region)) {
                                it.remove();
                                storage.add(player.getUniqueId(), plotRegions);
                                player.sendMessage(color("&bParcela eliminada con exíto."));
                            }
                        }
                    }

                    @Override
                    public void orElse() {

                    }
                };
                MenuBuilder menuBuilder = new ConfirmableActionMenu().getBuilder(6, confirmableAction);
                player.openInventory(menuBuilder.build());
            } else {
                player.sendMessage(color("&cEsa region no existe o no eres el dueño."));
            }
        } else {
            player.sendMessage(color("&cUso incorrecto! "+usage()));
        }
    }

    @Override
    public String identifier() {
        return "delete";
    }

    @Override
    public String usage() {
        return "&aUso: &c/parcelas delete &a<Id>";
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
}