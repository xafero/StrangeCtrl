package pl.grzeslowski.strangectrl.config;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;

public class Configuration {
	private final List<Button> buttons = new ArrayList<>();
	private Pov pov;

	private Configuration() {
		// for XStreamLoader
	}

	public Configuration(final Button... buttons) {
		for (final Button button : buttons) {
			this.buttons.add(button);
		}
	}

	public Configuration(final Pov pov) {
		this.pov = pov;
	}

	public Pov getPov() {
		return pov;
	}

	public void setPov(final Pov pov) {
		this.pov = pov;
	}

	public List<Button> getButtons() {
		return buttons;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(buttons, pov);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Configuration) {
			final Configuration configuration = (Configuration) obj;

			return Objects.equal(buttons, configuration.buttons)
					&& Objects.equal(pov, configuration.pov);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Configuration[" + Joiner.on(", ").join(buttons) + " | " + pov
				+ "]";
	}
}
