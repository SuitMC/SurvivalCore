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
import nahubar65.gmail.com.score.storages.WarpStorage;
import nahubar65.gmail.com.score.survivalcommands.core.SCoreCommand;
import nahubar65.gmail.com.score.survivalcommands.warps.Spawn;
import nahubar65.gmail.com.score.survivalcommands.warps.WarpCommand;
import nahubar65.gmail.com.score.survivalcommands.warps.WarpTeleport;
import team.unnamed.gui.MenuListeners;

import java.io.File;
import java.sql.Connection;

public class SurvivalService implements Service {

    private static Configuration configuration;

    private static Connection connection;

    private CommandLoader commandLoader;

    private WarpStorage warpStorage;

    private EventLoader eventLoader;

    private UUIDCache<Object> uuidCache;

    private PlayerInfoStorage settingsStorage;

    private BossBarService bossBarService;

    private MySQLConnector mySQLConnector;

    private ParticleEffectStorage particleEffectStorage;

    private RegionService plotService;

    public SurvivalService(SurvivalCore survivalCore) {
        this.mySQLConnector = new MySQLConnector("Kr3RwotSFxbNqF0U", "SurvivalCore", "localhost", "SurvivalCore");
        connection = mySQLConnector.getConnection();
        File folderData = new File(survivalCore.getFolder(), "playerdata");
        if (!folderData.exists()) {
            folderData.mkdirs();
        }
        this.configuration = new Configuration(survivalCore, new File(survivalCore.getFolder(), "globalConfig.yml"));
        this.settingsStorage = new PlayerInfoStorage(folderData);
        this.plotService = new RegionService(connection, survivalCore);
        this.bossBarService = new BossBarService(survivalCore, settingsStorage);
        this.uuidCache = new UUIDCache<>();
        this.particleEffectStorage = new ParticleEffectStorage(new Configuration(survivalCore, new File(survivalCore.getFolder(), "particles.yml")));
        this.warpStorage = new WarpStorage(configuration, plotService.getRegionStorage());
        this.eventLoader = new EventLoader(survivalCore,
                new TeleportCommandListener(uuidCache),
                new AbstractActionListeners(),
                new CustomEventListeners(),
                new PlayerListeners(settingsStorage, folderData),
                new MenuListeners());
        this.commandLoader = new CommandLoader(
                new SCoreCommand("score", warpStorage),
                new Spawn("spawn", warpStorage, uuidCache, survivalCore),
                new WarpTeleport("warp", warpStorage, uuidCache, survivalCore),
                new WarpCommand("warpmanager", warpStorage, plotService.getRegionStorage()),
                new ParticleEffectCommand("particles", particleEffectStorage));
        Service.register(this);
    }

    @Override
    public void start() {
        commandLoader.load();
        eventLoader.load();
        warpStorage.loadAll();
        bossBarService.start();
        plotService.start();
        particleEffectStorage.loadAll();
    }

    @Override
    public void stop() {
        settingsStorage.saveAll();
        warpStorage.saveAll();
        bossBarService.stop();
        plotService.stop();
    }

    @Override
    public String serviceIdentifier() {
        return "survivalService";
    }

    public static Configuration globalConfiguration() {
        return configuration;
    }
}