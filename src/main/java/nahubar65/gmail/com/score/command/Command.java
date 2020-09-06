package nahubar65.gmail.com.score.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class Command extends org.bukkit.command.Command {

    private List<CommandArgument> commandArgumentList;

    public Command(String name){
        super(name);
        commandArgumentList = new ArrayList<>();
    }

    @Override
    public boolean execute(CommandSender sender, String arg, String[] args) {
        if (useOnlyPlayer() && sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatColor.RED + "¡Only players can execute this command!");
            return true;
        }
        if (args.length > 0 || zeroArguments()) {
            if (executeCommand(sender, arg, args)) {
                if (!commandArgumentList.isEmpty()) {
                    for (CommandArgument commandArgument : commandArgumentList) {
                        if (args[0].equalsIgnoreCase(commandArgument.identifier())) {
                            if (commandArgument.useOnlyPlayer() && sender instanceof ConsoleCommandSender) {
                                sender.sendMessage(ChatColor.RED + "¡Only players can execute this command!");
                                return true;
                            }
                            String[] args2 = new String[args.length - 1];
                            for (int i = 1; i < args.length; i++) {
                                args2[i - 1] = args[i];
                            }
                            commandArgument.execute(sender, arg, args2);
                            return true;
                        }
                    }
                    if(getHelp(sender) != null && !getHelp(sender).isEmpty())
                        getHelp(sender).forEach(s -> {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
                        });
                }
            }
        } else {
            if(getHelp(sender) != null && !getHelp(sender).isEmpty())
                getHelp(sender).forEach(s -> {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
                });
        }
        return true;
    }

    public abstract boolean executeCommand(CommandSender sender, String arg, String[] args);

    public abstract boolean useOnlyPlayer();

    public abstract List<String> getHelp(CommandSender sender);

    public List<CommandArgument> getArguments() {
        return commandArgumentList;
    }

    public abstract boolean zeroArguments();

    public void registerNewArgument(CommandArgument commandArgument){
        for (CommandArgument argument : commandArgumentList) {
            if (argument.identifier() == commandArgument.identifier())
                return;
        }
        commandArgumentList.add(commandArgument);
    }

    public void register(){
        final Field bukkitCommandMap;
        try {
            bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap cmdMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            if(cmdMap.getCommand(this.getName()) != null) return;
            cmdMap.register(this.getName(), this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}