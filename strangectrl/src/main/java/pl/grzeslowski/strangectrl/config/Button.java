package pl.grzeslowski.strangectrl.config;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;

public class Button {
	private String value;
	private final List<Key> keys = new ArrayList<>();

	private Button() {
		// for XStreamLoader
	}

	public Button(final String value, final Key... keys) {
		this.value = value;

		for (final Key key : keys) {
			this.keys.add(key);
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public List<Key> getKeys() {
		return keys;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value, keys);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Button) {
			final Button button = (Button) obj;

			return Objects.equal(value, button.value)
					&& Objects.equal(keys, button.keys);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Button [value=" + value + ", keys=" + Joiner.on(",").join(keys)
				+ "]";
	}

}
