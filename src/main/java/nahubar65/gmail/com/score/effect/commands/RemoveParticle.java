package nahubar65.gmail.com.score.effect.commands;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.storages.ParticleEffectStorage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class RemoveParticle extends CommandArgument {

    private ParticleEffectStorage particleEffectStorage;

    public RemoveParticle(Command command, ParticleEffectStorage particleEffectStorage) {
        super(command);
        this.particleEffectStorage = particleEffectStorage;
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        if (args.length > 0) {
            String arg1 = args[0];
            if (particleEffectStorage.get().containsKey(arg1)) {
                particleEffectStorage.remove(arg1);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bParticula &9"+arg1+" &bremovida!"));
                return;
            }
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUso incorrecto! "+usage()));
    }

    @Override
    public String identifier() {
        return "remove";
    }

    @Override
    public String usage() {
        return "&aUso: /particles remove <Llave>";
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