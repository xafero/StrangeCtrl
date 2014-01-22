package pl.grzeslowski.strangectrl.config;

import java.util.List;

import com.google.common.collect.Lists;

public class SouthEastPov extends PovDirection {
	private SouthEastPov() {
		// for XStreamLoader
	}

	public SouthEastPov(final Key... keys) {
		this(Lists.newArrayList(keys));
	}

	public SouthEastPov(final List<Key> keys) {
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
		return "SEP";
	}
}
