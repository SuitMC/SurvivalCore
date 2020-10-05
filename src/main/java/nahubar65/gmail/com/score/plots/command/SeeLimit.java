package nahubar65.gmail.com.score.plots.command;

import nahubar65.gmail.com.score.SurvivalCore;
import nahubar65.gmail.com.score.colors.CustomColors;
import nahubar65.gmail.com.score.command.Command;
import nahubar65.gmail.com.score.command.CommandArgument;
import nahubar65.gmail.com.score.particle.ParticleModel;
import nahubar65.gmail.com.score.plots.PlotRegion;
import nahubar65.gmail.com.score.plots.SimplePlotRegion;
import nahubar65.gmail.com.score.regions.Region;
import nahubar65.gmail.com.score.regions.RegionUtils;
import nahubar65.gmail.com.score.storages.Storage;
import nahubar65.gmail.com.score.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class SeeLimit extends CommandArgument {

    private Storage<UUID, List<PlotRegion>> storage;

    private List<UUID> uuidList;

    public SeeLimit(Command command, Storage<UUID, List<PlotRegion>> storage) {
        super(command);
        this.storage = storage;
        this.uuidList = new ArrayList<>();
    }

    @Override
    public void execute(CommandSender sender, String arg, String[] args) {
        Player player = (Player) sender;
        if (args.length > 0) {
            String arg1 = args[0];
            PlotRegion region = Utils.findFromName(arg1, storage);
            if (region != null) {
                if (region.getMemberManager().isMember(player)) {
                    seeLimits(region.getRegion(), player);
                }
            } else {
                player.sendMessage("&c¡Esa region no existe!");
            }
        }
    }

    @Override
    public String identifier() {
        return "limit";
    }

    @Override
    public String usage() {
        return "/parcelas <Region> limit";
    }

    @Override
    public boolean useOnlyPlayer() {
        return true;
    }

    @Override
    public boolean canExecute(CommandSender commandSender) {
        Player player = (Player) commandSender;
        return !uuidList.contains(player.getUniqueId());
    }

    @Override
    public void ifCantExecute(CommandSender commandSender) {
        commandSender.sendMessage(color("&cDebes esperar antes de usar ese comando de nuevo!"));
    }

    private void seeLimits(Region region, Player player) {
        ParticleModel particleModel = ParticleModel.coloredParticle(CustomColors.AQUA);
        new BukkitRunnable() {

            private int cooldown = 0;

            @Override
            public void run() {
                if (cooldown >= 30) {
                    uuidList.remove(player.getUniqueId());
                    this.cancel();
                }
                RegionUtils.sendLimits(particleModel, region, player);
                cooldown += 1;
            }
        }.runTaskTimer(SurvivalCore.plugin(), 0, 05);
        uuidList.add(player.getUniqueId());
    }
}