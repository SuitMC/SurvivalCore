package nahubar65.gmail.com.score.storages;

import com.google.gson.Gson;
import nahubar65.gmail.com.score.db.DBMapWriter;
import nahubar65.gmail.com.score.plots.PlotRegion;
import nahubar65.gmail.com.score.plots.SimplePlotRegion;
import nahubar65.gmail.com.score.regions.GlobalRegionContainer;

import java.sql.Connection;
import java.util.*;

public class SQLPlotStorage implements Storage<UUID, List<PlotRegion>> {

    private final Map<UUID, List<PlotRegion>> uuidListMap = new HashMap<>();

    private final DBMapWriter dbMapWriter;

    private GlobalRegionContainer globalRegionContainer;

    public SQLPlotStorage(Connection connection, GlobalRegionContainer globalRegionContainer) {
        this.dbMapWriter = new DBMapWriter(connection, "plots", "UUID");
        this.globalRegionContainer = globalRegionContainer;
    }

    @Override
    public Map<UUID, List<PlotRegion>> get() {
        return uuidListMap;
    }

    @Override
    public Optional<List<PlotRegion>> find(UUID key) {
        List<PlotRegion> plotRegionList;
        if (!get().containsKey(key)) {
            plotRegionList = new ArrayList<>();
        } else {
            plotRegionList = get().get(key);
        }
        return Optional.of(plotRegionList);
    }

    @Override
    public Optional<List<PlotRegion>> findFromData(UUID key) {
        List<PlotRegion> plotRegionList = new ArrayList<>();
        String keyString = key.toString();
        Gson gson = new Gson();
        Map<String, Object> objectMap = dbMapWriter.get(keyString);
        List<Map<String, Object>> mapList = gson.fromJson((String) objectMap.get("region"), List.class);
        for (Map<String, Object> mapFromJson : mapList) {
            PlotRegion plotRegion = PlotRegion.deserialize(mapFromJson);
            if(plotRegion != null)
                plotRegionList.add(plotRegion);
        }
        return Optional.of(plotRegionList);
    }

    @Override
    public void save(UUID key) {
        find(key).ifPresent(plotRegions -> {
            Gson gson = new Gson();
            List<Map<String, Object>> mapList = new ArrayList<>();
            for (PlotRegion plotRegion : plotRegions) {
                mapList.add(plotRegion.serialize());
            }
            dbMapWriter.set(key.toString(), gson.toJson(mapList, List.class));
        });
    }

    @Override
    public void saveObject(UUID key, List<PlotRegion> value) {

    }

    @Override
    public void remove(UUID key) {

    }

    @Override
    public void add(UUID key, List<PlotRegion> value) {
        uuidListMap.put(key, value);
        for (PlotRegion plotRegion : value) {
            if (!globalRegionContainer.get().contains(plotRegion))
                globalRegionContainer.get().add(plotRegion.getRegion());
        }
    }

    private void add(UUID uuid, SimplePlotRegion plotRegion) {
        Optional<List<PlotRegion>> optionalList = find(uuid);
        List<PlotRegion> plotRegions = new ArrayList<>();
        if (optionalList.isPresent()) {
            plotRegions = optionalList.get();
        }
        plotRegions.add(plotRegion);
        add(uuid, plotRegions);
    }

    @Override
    public void saveAll() {
        uuidListMap.forEach((K, V) -> {
            save(K);
        });
    }

    @Override
    public void loadAll() {
        for (String key : dbMapWriter.getKeys()) {
            UUID uuid = UUID.fromString(key);
            findFromData(uuid).ifPresent(plotRegions -> {
                add(uuid, plotRegions);
            });
        }
    }
}