package nahubar65.gmail.com.score.survivalcommands.warps;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.storages.WarpStorage;
import nahubar65.gmail.com.score.utils.Warp;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SetWarp extends CommandArgument {

    private WarpStorage warpStorage;

    public SetWarp(Command command, WarpStorage warpStorage) {
        super(command);
        this.warpStorage = warpStorage;
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        if (args.length > 0) {
            Player player = (Player) sender;
            String arg1 = args[0];
            Optional<Warp> optionalWarp = warpStorage.find(arg1);
            Warp warp;
            if (!optionalWarp.isPresent()) {
                warp = new Warp(player.getLocation(), arg1);
                warpStorage.add(arg1, warp);
            }else{
                warp = optionalWarp.get();
                warp.setSpawnPoint(player.getLocation());
            }
            sender.sendMessage(color("&a¡Warp &9"+arg1+" &acolocado con exíto!"));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', usage()));
        }
    }

    @Override
    public String identifier() {
        return "setwarp";
    }

    @Override
    public String usage() {
        return "/score setwarp <Nombre>";
    }

    @Override
    public boolean useOnlyPlayer() {
        return true;
    }

    @Override
    public String getPermission() {
        return "score.admin.*";
    }

    private String color(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}