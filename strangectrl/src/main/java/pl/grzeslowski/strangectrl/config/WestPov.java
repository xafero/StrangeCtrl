package pl.grzeslowski.strangectrl.config;

import java.util.List;

import com.google.common.collect.Lists;

public class WestPov extends PovDirection {
	private WestPov() {
		// for XStreamLoader
	}

	public WestPov(final Key... keys) {
		this(Lists.newArrayList(keys));
	}

	public WestPov(final List<Key> keys) {
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
		return "WP";
	}
}
