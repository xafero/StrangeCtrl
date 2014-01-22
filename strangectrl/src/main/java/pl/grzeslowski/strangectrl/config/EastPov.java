package pl.grzeslowski.strangectrl.config;

import java.util.List;

import com.google.common.collect.Lists;

public class EastPov extends PovDirection {
	private EastPov() {
		// for XStreamLoader
	}

	public EastPov(final Key... keys) {
		this(Lists.newArrayList(keys));
	}

	public EastPov(final List<Key> keys) {
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
		return "EP";
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof EastPov) {
			final EastPov pov = (EastPov) obj;

			return super.equals(pov);
		} else {
			return false;
		}
	}

}
