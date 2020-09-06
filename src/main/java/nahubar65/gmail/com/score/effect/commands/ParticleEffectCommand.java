package nahubar65.gmail.com.score.effect.commands;

import javafx.scene.paint.Color;
import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.storages.ParticleEffectStorage;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ParticleEffectCommand extends Command {

    public ParticleEffectCommand(String name, ParticleEffectStorage particleEffectStorage) {
        super(name);
        registerNewArgument(new CreateParticle(this, particleEffectStorage));
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
        help.add("&a/particles create &6-> &9&oCrea un nuevo efecto de particulas");
        help.add("&aTipos de efectos:");
        help.add("&bSPIRAL(Parametros: &bradio, Particula/Color, ticks, limiteDeAltura, espacioEntreParticulas (Opcional))");
        help.add("&bCIRCLE(Parametros: &bradio, Particula/Color, ticks, limiteDeAltura)");
        help.add("&ePara ver la lista de colores ingrese aquí: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/paint/Color.html");
        return help;
    }

    @Override
    public boolean zeroArguments() {
        return false;
    }
}