package nahubar65.gmail.com.score.survivalcommands.warps;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.storages.WarpStorage;
import nahubar65.gmail.com.score.utils.Warp;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Optional;

public class DelWarp extends CommandArgument {

    private WarpStorage warpStorage;

    public DelWarp(Command command, WarpStorage warpStorage) {
        super(command);
        this.warpStorage = warpStorage;
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        if (args.length > 0) {
            String arg1 = args[0];
            Optional<Warp> warpOptional = warpStorage.find(arg1);
            if (warpOptional.isPresent()) {
                warpStorage.remove(arg1);
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUso incorrecto! "+usage()));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUso incorrecto! "+usage()));
        }
    }

    @Override
    public String identifier() {
        return "delwarp";
    }

    @Override
    public String usage() {
        return "&aUso: &b/warpmanager delwarp";
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
