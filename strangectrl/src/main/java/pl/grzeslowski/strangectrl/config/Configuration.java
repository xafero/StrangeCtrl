package pl.grzeslowski.strangectrl.config;

import java.util.List;

public class Configuration {
    private List<Button> buttons;
    private Pov pov;

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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Configuration other = (Configuration) obj;
        if (buttons == null) {
            if (other.buttons != null) {
                return false;
            }
        } else if (!buttons.equals(other.buttons)) {
            return false;
        }
        if (pov == null) {
            if (other.pov != null) {
                return false;
            }
        } else if (!pov.equals(other.pov)) {
            return false;
        }
        return true;
    }

}
