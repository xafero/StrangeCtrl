package pl.grzeslowski.strangectrl.cmd.mappers;

import java.util.HashMap;
import java.util.Map;

public abstract class MapNameMapper implements CommandNameMapper {

    private final Map<String, String> map = new HashMap<>();

    public MapNameMapper(final Map<String, String> map) {
        this.map.putAll(map);
    }

    protected boolean canMap(final String identifier) {
        return map.containsKey(identifier);
    }

    public String map(final String identifier) {
        return map.get(identifier);
    }

}
