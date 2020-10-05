package nahubar65.gmail.com.score.services;

import nahubar65.gmail.com.score.SurvivalCore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

public interface Service {

    void start();

    void stop();

    String serviceIdentifier();

    static void register(Service service){
        Bukkit.getServicesManager().register(Service.class, service, SurvivalCore.plugin(), ServicePriority.Lowest);
    }
}