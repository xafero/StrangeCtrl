package pl.grzeslowski.strangectrl.config;

import java.util.List;

import com.google.common.collect.Lists;

public class SouthWestPov extends PovDirection {
	private SouthWestPov() {
		// for XStreamLoader
	}

	public SouthWestPov(final Key... keys) {
		this(Lists.newArrayList(keys));
	}

	public SouthWestPov(final List<Key> keys) {
		for (final Key key : keys) {
			this.keys.add(key);
		}
	}

	@Override
	public List<Key> getKeys() {
		return keys;
	}

	@Override
	public String getIdentifier() {
		return "SWP";
	}
}
