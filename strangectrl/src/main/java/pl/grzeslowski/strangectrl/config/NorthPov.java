package pl.grzeslowski.strangectrl.config;

import java.util.List;

import com.google.common.collect.Lists;

public class NorthPov extends PovDirection {
	private NorthPov() {
		// for XStreamLoader
	}

	public NorthPov(final Key... keys) {
		this(Lists.newArrayList(keys));
	}

	public NorthPov(final List<Key> keys) {
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
		return "NP";
	}
}
