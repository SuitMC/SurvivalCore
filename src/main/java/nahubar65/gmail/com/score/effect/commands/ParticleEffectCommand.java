package nahubar65.gmail.com.score.effect.commands;

import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.storages.ParticleEffectStorage;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ParticleEffectCommand extends Command {

    public ParticleEffectCommand(String name, ParticleEffectStorage particleEffectStorage) {
        super(name);
        registerNewArgument(new CreateParticle(this, particleEffectStorage));
        registerNewArgument(new ParticleList(this, particleEffectStorage));
        registerNewArgument(new RemoveParticle(this, particleEffectStorage));
    }

    @Override
    public boolean executeCommand(CommandSender sender, String arg, String[] args) {
        return true;
    }

    @Override
    public boolean useOnlyPlayer() {
        return true;
    }

    @Override
    public List<String> getHelp(CommandSender sender) {
        List<String> help = new ArrayList<>();
        help.add("&a/particles create &6-> &9&oCrea un nuevo efecto de particulas.");
        help.add("&a/particles remove <Llave> &6-> &9&oElimina un efecto de particulas existente.");
        help.add("&a/particles list &6-> &9&oMira la lista de efectos existentes.");
        help.add("&aTipos de efectos:");
        help.add("&bSPIRAL(Parametros: &bradio, Particula/Color, ticks, limiteDeAltura, espacioEntreParticulas (Opcional))");
        help.add("&bCIRCLE(Parametros: &bradio, Particula/Color, ticks, espacioEntreParticulas (opcional))");
        help.add("&ePara ver la lista de colores ingrese aquí: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/paint/Color.html");
        return help;
    }

    @Override
    public boolean zeroArguments() {
        return false;
    }
}