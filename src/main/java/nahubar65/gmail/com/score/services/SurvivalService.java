package nahubar65.gmail.com.score.services;

import nahubar65.gmail.com.score.SurvivalCore;
import nahubar65.gmail.com.score.caches.UUIDCache;
import nahubar65.gmail.com.score.configuration.Configuration;
import nahubar65.gmail.com.score.db.MySQLConnector;
import nahubar65.gmail.com.score.effect.commands.ParticleEffectCommand;
import nahubar65.gmail.com.score.listeners.*;
import nahubar65.gmail.com.score.loader.CommandLoader;
import nahubar65.gmail.com.score.loader.EventLoader;
import nahubar65.gmail.com.score.storages.ParticleEffectStorage;
import nahubar65.gmail.com.score.storages.PlayerInfoStorage;
import nahubar65.gmail.com.score.storages.RegionStorage;
import nahubar65.gmail.com.score.storages.WarpStorage;
import nahubar65.gmail.com.score.survivalcommands.regions.RegionCommand;
import nahubar65.gmail.com.score.survivalcommands.core.SCoreCommand;
import nahubar65.gmail.com.score.survivalcommands.warps.Spawn;
import nahubar65.gmail.com.score.survivalcommands.warps.WarpCommand;
import nahubar65.gmail.com.score.survivalcommands.warps.WarpTeleport;
import nahubar65.gmail.com.score.utils.Pair;
import org.bukkit.Location;

import java.io.File;
import java.sql.Connection;

public class SurvivalService implements Service {

    private Configuration configuration;

    private CommandLoader commandLoader;

    private WarpStorage warpStorage;

    private EventLoader eventLoader;

    private UUIDCache<Object> uuidCache;

    private UUIDCache<Pair<Location, Location>> pairUUIDCache;

    private RegionStorage regionStorage;

    private PlayerInfoStorage settingsStorage;

    private BossBarService bossBarService;

    private static Connection connection;

    private MySQLConnector mySQLConnector;

    private ParticleEffectStorage particleEffectStorage;

    public SurvivalService(SurvivalCore survivalCore){
        this.mySQLConnector = new MySQLConnector("Kr3RwotSFxbNqF0U", "SurvivalCore", "localhost", "SurvivalCore");
        connection = mySQLConnector.getConnection();
        this.configuration = new Configuration(survivalCore, new File(survivalCore.getFolder(), "globalconfig.yml"));
        this.regionStorage = new RegionStorage(survivalCore);
        this.warpStorage = new WarpStorage(configuration, regionStorage);
        this.uuidCache = new UUIDCache<>();
        this.pairUUIDCache = new UUIDCache<>();
        this.particleEffectStorage = new ParticleEffectStorage(new Configuration(survivalCore, new File(survivalCore.getFolder(), "particles.yml")));
        this.commandLoader = new CommandLoader(
                new SCoreCommand("score", warpStorage),
                new Spawn("spawn", warpStorage, uuidCache, survivalCore),
                new WarpTeleport("warp", warpStorage, uuidCache, survivalCore),
                new RegionCommand("region", pairUUIDCache, regionStorage),
                new WarpCommand("warpmanager", warpStorage, regionStorage),
                new ParticleEffectCommand("particles", particleEffectStorage));
        File folderData = new File(survivalCore.getFolder(), "playerdata");
        if (!folderData.exists()) {
            folderData.mkdirs();
        }
        this.settingsStorage = new PlayerInfoStorage(folderData);
        this.bossBarService = new BossBarService(survivalCore, settingsStorage);
        this.eventLoader = new EventLoader(survivalCore,
                new TeleportCommandListener(uuidCache),
                new AbstractActionListeners(),
                new RegionListeners(regionStorage),
                new CustomEventListeners(),
                new PlayerListeners(settingsStorage, folderData));
    }

    @Override
    public void start() {
        commandLoader.load();
        eventLoader.load();
        regionStorage.loadAll();
        warpStorage.loadAll();
        bossBarService.start();
        particleEffectStorage.loadAll();
    }

    @Override
    public void stop() {
        settingsStorage.saveAll();
        warpStorage.saveAll();
        regionStorage.saveAll();
        bossBarService.stop();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public static Connection getConnection() {
        return connection;
    }
}