package nahubar65.gmail.com.score.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class CommandArgument {

    protected Command command;

    public CommandArgument(Command command){
        this.command = command;
    }

    public void executeSubCommand(CommandSender sender, String arg, String[] args) {
        if (canExecute(sender)) {
            execute(sender, arg, args);
        } else {
            ifCantExecute(sender);
        }
    }

    public abstract void execute(CommandSender sender, String arg, String[] args);

    public abstract String identifier();

    public abstract String usage();

    public abstract boolean useOnlyPlayer();

    public abstract boolean canExecute(CommandSender commandSender);

    public abstract void ifCantExecute(CommandSender commandSender);

    public String color(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
