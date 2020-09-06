package nahubar65.gmail.com.score.effect.commands;

import fr.mrmicky.fastparticle.ParticleType;
import nahubar65.gmail.com.score.colors.Color;
import nahubar65.gmail.com.score.colors.CustomColors;
import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.effect.CircleParticleEffect;
import nahubar65.gmail.com.score.effect.ParticleEffect;
import nahubar65.gmail.com.score.effect.ParticleEffectType;
import nahubar65.gmail.com.score.effect.SpiralParticleEffect;
import nahubar65.gmail.com.score.particle.ParticleModel;
import nahubar65.gmail.com.score.particle.SimpleParticleModelImpl;
import nahubar65.gmail.com.score.storages.ParticleEffectStorage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateParticle extends CommandArgument {

    private ParticleEffectStorage particleEffectStorage;

    public CreateParticle(Command command, ParticleEffectStorage particleEffectStorage) {
        super(command);
        this.particleEffectStorage = particleEffectStorage;
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        if (args.length > 4) {
            try {
                ParticleEffectType particleEffectType = ParticleEffectType.valueOf(args[0]);

                double radius = Double.valueOf(args[1]);

                String arg3 = args[2];

                Color color;

                ParticleType particleType;

                ParticleModel simpleParticleModel = null;

                Location location = ((Player) sender).getLocation();

                ParticleEffect particleEffect;

                if ((color = getColor(arg3)) != null) {
                    simpleParticleModel = ParticleModel.coloredParticle(color);
                } else if ((particleType = getType(arg3)) != null) {
                    simpleParticleModel = new SimpleParticleModelImpl(particleType, 1);
                } else {
                    sender.sendMessage(usage());
                    return;
                }

                int ticks = Integer.valueOf(args[3]);

                double limit = Double.valueOf(args[4]);

                switch (particleEffectType) {
                    case CIRCLE:
                        particleEffect = new CircleParticleEffect(location.getWorld(),
                                radius,
                                location,
                                simpleParticleModel,
                                limit);
                        particleEffect.start(ticks);
                        particleEffectStorage.add(particleEffect);
                        break;
                    case SPIRAL:
                        if (args.length > 4) {
                            int spaceBetweenParticles = 1;
                            if (args.length > 5) {
                                spaceBetweenParticles = Integer.valueOf(args[4]);
                            }
                            particleEffect = new SpiralParticleEffect(location.getWorld(),
                                    radius,
                                    simpleParticleModel,
                                    location,
                                    limit,
                                    spaceBetweenParticles);
                            particleEffect.start(ticks);
                            particleEffectStorage.add(particleEffect);
                        } else {
                           sender.sendMessage(usage());
                        }
                        break;
                    default:
                        break;
                }
            }catch (Exception e) {
                sender.sendMessage(usage());
            }
        } else {
            sender.sendMessage(usage());
        }
    }

    @Override
    public String identifier() {
        return "create";
    }

    @Override
    public String usage() {
        return ChatColor.translateAlternateColorCodes('&', "&aUso: /particles create <Tipo> <Parametros>");
    }

    @Override
    public boolean useOnlyPlayer() {
        return true;
    }

    @Override
    public String getPermission() {
        return "score.particles.create";
    }

    private Color getColor(String s){
        try {
            return CustomColors.valueOf(s);
        }catch (Exception e){
            return null;
        }
    }

    private ParticleType getType(String s){
        try {
            return ParticleType.valueOf(s);
        }catch (Exception e){
            return null;
        }
    }
}