package pl.grzeslowski.strangectrl.cmd.mappers;

import java.util.HashMap;
import java.util.Map;

public class XboxNameMapper extends MapNameMapper {
    public XboxNameMapper() {
        super(createMap());
    }

    private static Map<String, String> createMap() {
        final Map<String, String> map = new HashMap<>();

        map.put("0", "A");
        map.put("1", "B");
        map.put("2", "X");
        map.put("3", "Y");
        map.put("4", "LB");
        map.put("5", "RB");
        map.put("6", "BACK");
        map.put("7", "START");
        map.put("8", "LS");
        map.put("9", "RS");

        return map;
    }

    @Override
    public boolean canMap(final String identifier, final double value) {
        return super.canMap(identifier);
    }

    @Override
    public String map(final String identifier, final double value) {
        return super.map(identifier);
    }

}
