package pl.grzeslowski.strangectrl.cmd;

import static com.google.common.base.Objects.equal;

import java.util.HashMap;
import java.util.Map;

public class PovNameMapper implements CommandNameMapper {

	private static final String POV = "pov";

	private final Map<Double, String> map = new HashMap<>();

	public PovNameMapper() {
		map.put(0.125, "NWP");
		map.put(0.25, "NP");
		map.put(0.375, "NEP");
		map.put(0.5, "EP");
		map.put(0.625, "SEP");
		map.put(0.75, "SP");
		map.put(0.875, "SWP");
		map.put(1.0, "WP");
	}

	@Override
	public boolean canMap(final String identifier, final double value) {
		return equal(POV, identifier) && map.containsKey(value);
	}

	@Override
	public String map(final String identifier, final double value) {
		return map.get(value);
	}

}
