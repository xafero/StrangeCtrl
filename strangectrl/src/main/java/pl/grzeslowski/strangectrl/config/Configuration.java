package pl.grzeslowski.strangectrl.config;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;

public class Configuration {
    private final List<Button> buttons = new ArrayList<>();
    private Pov pov;

    public Configuration() {
    }

    public Configuration(final Button... buttons) {
        for (final Button button : buttons) {
            this.buttons.add(button);
        }
    }

    public Configuration(final Pov pov) {
        this.pov = pov;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (buttons == null ? 0 : buttons.hashCode());
        result = prime * result + (pov == null ? 0 : pov.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Configuration && equals((Configuration) obj);
    }

    private boolean equals(final Configuration obj) {
        if (obj == null) {
            return false;
        }

        return Objects.equal(pov, obj.pov)
                && Iterables.elementsEqual(buttons, obj.buttons);
    }

    @Override
    public String toString() {
        return "Configuration[" + Joiner.on(", ").join(buttons) + " | " + pov
                + "]";
    }
}
