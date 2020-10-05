package nahubar65.gmail.com.score.regions;

import nahubar65.gmail.com.score.container.ObjectContainer;

import java.util.HashSet;
import java.util.Set;

public class GlobalRegionContainer implements ObjectContainer<Region> {

    private final Set<Region> regionSet;

    public GlobalRegionContainer(){
        this.regionSet = new HashSet<>();
    }

    public Set<Region> get() {
        return regionSet;
    }
}