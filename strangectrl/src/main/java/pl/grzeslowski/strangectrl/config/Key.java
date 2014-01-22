package pl.grzeslowski.strangectrl.config;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;

public class Key {
	private String key;

	private Key() {
		// for XStreamLoader
	}

	public Key(final String key) {
		this.key = checkNotNull(key);
	}

	public String getKey() {
		return key;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(key);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Key) {
			final Key key = (Key) obj;

			return equal(this.key, key.key);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Key[" + key + "]";
	}
}
