package nahubar65.gmail.com.score.services;

import nahubar65.gmail.com.score.SurvivalCore;
import nahubar65.gmail.com.score.caches.UUIDCache;
import nahubar65.gmail.com.score.listeners.PlotListeners;
import nahubar65.gmail.com.score.listeners.RegionListeners;
import nahubar65.gmail.com.score.loader.CommandLoader;
import nahubar65.gmail.com.score.loader.EventLoader;
import nahubar65.gmail.com.score.plots.PlotOptions;
import nahubar65.gmail.com.score.plots.PlotManager;
import nahubar65.gmail.com.score.plots.SimplePlotManager;
import nahubar65.gmail.com.score.plots.command.PlotCommand;
import nahubar65.gmail.com.score.regions.GlobalRegionContainer;
import nahubar65.gmail.com.score.storages.RegionStorage;
import nahubar65.gmail.com.score.storages.SQLPlotStorage;
import nahubar65.gmail.com.score.survivalcommands.regions.RegionCommand;
import nahubar65.gmail.com.score.utils.Pair;
import org.bukkit.Location;

import java.sql.Connection;

public class RegionService implements Service {

    private SQLPlotStorage sqlPlotStorage;

    private CommandLoader commandLoader;

    private UUIDCache<Pair<Location, Location>> uuidCache;

    private UUIDCache<Pair<Location, Location>> pairUUIDCache;

    private EventLoader eventLoader;

    private RegionStorage regionStorage;

    private static PlotManager plotManager;

    private GlobalRegionContainer globalRegionContainer;

    private static PlotOptions plotOptions;

    public RegionService(Connection connection, SurvivalCore survivalCore) {
        this.uuidCache = new UUIDCache<>();
        this.pairUUIDCache = new UUIDCache<>();
        this.globalRegionContainer = new GlobalRegionContainer();
        this.regionStorage = new RegionStorage(survivalCore, globalRegionContainer);
        this.sqlPlotStorage = new SQLPlotStorage(connection, globalRegionContainer);
        plotManager = new SimplePlotManager(SurvivalService.globalConfiguration().getInt("regions.price-per-block"),
                sqlPlotStorage);
        this.commandLoader = new CommandLoader(
                new PlotCommand("parcelas", uuidCache, sqlPlotStorage, regionStorage),
                new RegionCommand("region", pairUUIDCache, regionStorage));
        this.eventLoader = new EventLoader((SurvivalCore) SurvivalCore.plugin(),
                new PlotListeners(sqlPlotStorage),
                new RegionListeners(globalRegionContainer));
        this.plotOptions = new PlotOptions(SurvivalService.globalConfiguration(), sqlPlotStorage);
        Service.register(this);
    }

    @Override
    public void start() {
        commandLoader.load();
        sqlPlotStorage.loadAll();
        regionStorage.loadAll();
        eventLoader.load();
        plotOptions.load();
    }

    @Override
    public void stop() {
        sqlPlotStorage.saveAll();
        regionStorage.saveAll();
    }

    @Override
    public String serviceIdentifier() {
        return "regionService";
    }

    public static PlotManager getPlotManager() {
        return plotManager;
    }

    public RegionStorage getRegionStorage() {
        return regionStorage;
    }

    public static PlotOptions getPlotOptions() {
        return plotOptions;
    }
}