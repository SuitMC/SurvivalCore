package nahubar65.gmail.com.score.effect.commands;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.effect.ParticleEffect;
import nahubar65.gmail.com.score.storages.ParticleEffectStorage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.text.NumberFormat;
import java.util.Map;

public class ParticleList extends CommandArgument {

    private ParticleEffectStorage particleEffectStorage;

    public ParticleList(Command command, ParticleEffectStorage particleEffectStorage) {
        super(command);
        this.particleEffectStorage = particleEffectStorage;
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        for (Map.Entry<String, ParticleEffect> effectEntry : particleEffectStorage.get().entrySet()) {
            String format = "&b<Key> - &a<Type> &e| <Location>";
            String key = effectEntry.getKey();
            ParticleEffect particleEffect = effectEntry.getValue();
            format = format.replace("<Key>", key);
            Location location = particleEffect.getLocation();
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(3);
            String locationToString = "&eworld: &b"+location.getWorld().getName()+"," +
                    " &ex: &b"+numberFormat.format(location.getX()) +
                    " &ey: &b"+numberFormat.format(location.getY()) +
                    " &ez: &b"+numberFormat.format(location.getZ());
            format = format.replace("<Type>", particleEffect.getType().name());
            format = format.replace("<Location>", locationToString);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', format));
        }
    }

    @Override
    public String identifier() {
        return "list";
    }

    @Override
    public String usage() {
        return "/particles list";
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