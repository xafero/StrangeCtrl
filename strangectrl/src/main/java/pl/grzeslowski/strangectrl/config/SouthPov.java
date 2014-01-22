package pl.grzeslowski.strangectrl.config;

import java.util.List;

import com.google.common.collect.Lists;

public class SouthPov extends PovDirection {
	private SouthPov() {
		// for XStreamLoader
	}

	public SouthPov(final Key... keys) {
		this(Lists.newArrayList(keys));
	}

	public SouthPov(final List<Key> keys) {
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
		return "SP";
	}
}
