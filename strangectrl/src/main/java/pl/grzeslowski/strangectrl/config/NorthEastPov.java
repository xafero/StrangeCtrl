package pl.grzeslowski.strangectrl.config;

import java.util.List;

import com.google.common.collect.Lists;

public class NorthEastPov extends PovDirection {
	private NorthEastPov() {
		// for XStreamLoader
	}

	public NorthEastPov(final Key... keys) {
		this(Lists.newArrayList(keys));
	}

	public NorthEastPov(final List<Key> keys) {
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
		return "NEP";
	}

}
