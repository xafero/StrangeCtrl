package pl.grzeslowski.strangectrl.config;

import static com.google.common.base.Objects.equal;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

public abstract class PovDirection {
	protected final List<Key> keys = new ArrayList<>();

	public abstract List<Key> getKeys();

	public abstract String getIdentifier();

	@Override
	public int hashCode() {
		return Objects.hashCode(keys);
	}

	@Override
	public boolean equals(final Object obj) {
		if (equal(this.getClass(), obj.getClass())) {
			final PovDirection pov = (PovDirection) obj;

			return equal(keys, pov.keys);
		} else {
			return false;
		}
	}
}
