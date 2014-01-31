package pl.grzeslowski.strangectrl.config;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;

public class Configuration {
    private final List<Button> buttons = new ArrayList<>();
    private Pov pov;
    private Setup setup = Setup.getDefaultSetup();

    private Configuration() {
        // for XStreamLoader
    }

    public Configuration(final Button... buttons) {
        this(Arrays.asList(buttons), null);
    }

    public Configuration(final Pov pov) {
        this(new ArrayList<Button>(), pov);
    }

    public Configuration(final Setup setup) {
        this.setup = setup;
    }

    public Configuration(final List<Button> buttons, final Pov pov) {
        this.buttons.addAll(checkNotNull(buttons));
        this.pov = pov;

    }

    public Pov getPov() {
        return pov;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public Setup getSetup() {
        return setup;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(buttons, pov, setup);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Configuration) {
            final Configuration configuration = (Configuration) obj;

            return Objects.equal(buttons, configuration.buttons)
                    && Objects.equal(pov, configuration.pov)
                    && equal(setup, configuration.setup);
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
