package nahubar65.gmail.com.score.survivalcommands.regions;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.regions.RegionFlag;
import org.bukkit.command.CommandSender;

public class FlagList extends CommandArgument {

    public FlagList(Command command) {
        super(command);
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        for (RegionFlag value : RegionFlag.values()) {
            sender.sendMessage(color("&b"+value.name()));
        }
    }

    @Override
    public String identifier() {
        return "flaglist";
    }

    @Override
    public String usage() {
        return "/region flaglist";
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
}