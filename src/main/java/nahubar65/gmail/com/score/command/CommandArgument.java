package nahubar65.gmail.com.score.command;

import org.bukkit.command.CommandSender;

public abstract class CommandArgument {

    private Command command;

    public CommandArgument(Command command){
        this.command = command;
    }

    public abstract void execute(CommandSender sender, String arg, String[] args);

    public abstract String identifier();

    public abstract String usage();

    public abstract boolean useOnlyPlayer();

    public abstract String getPermission();
}
