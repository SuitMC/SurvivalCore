package nahubar65.gmail.com.score.survivalcommands.warps;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.storages.WarpStorage;
import nahubar65.gmail.com.score.utils.Warp;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SetSpawn extends CommandArgument {

    private WarpStorage warpStorage;

    public SetSpawn(Command command, WarpStorage warpStorage) {
        super(command);
        this.warpStorage = warpStorage;
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        Player player = (Player) sender;
        Optional<Warp> spawn = warpStorage.find("spawn");
        if(!spawn.isPresent()){
            spawn = Optional.of(new Warp(player.getLocation(), "spawn"));
            warpStorage.add("spawn", spawn.get());
        } else {
            spawn.get().setSpawnPoint(player.getLocation());
        }
        sender.sendMessage(color("&a¡Spawn colocado con exíto!"));
    }

    @Override
    public String identifier() {
        return "setspawn";
    }

    @Override
    public String usage() {
        return "/score setspawn";
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