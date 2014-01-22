package pl.grzeslowski.strangectrl.config;

import java.util.List;

import com.google.common.collect.Lists;

public class NorthWestPov extends PovDirection {
	private NorthWestPov() {
		// for XStreamLoader
	}

	public NorthWestPov(final Key... keys) {
		this(Lists.newArrayList(keys));
	}

	public NorthWestPov(final List<Key> keys) {
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
		return "NWP";
	}
}
