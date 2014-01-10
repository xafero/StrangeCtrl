package pl.grzeslowski.strangectrl.config;

import java.util.ArrayList;
import java.util.List;

public class SouthPov {
    private final List<Key> keys = new ArrayList<>();
    private final List<State> states = new ArrayList<>();

    public SouthPov() {
    }

    public SouthPov(final Key... keys) {
        for (final Key key : keys) {
            this.keys.add(key);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (keys == null ? 0 : keys.hashCode());
        result = prime * result + (states == null ? 0 : states.hashCode());
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
        final SouthPov other = (SouthPov) obj;
        if (keys == null) {
            if (other.keys != null) {
                return false;
            }
        } else if (!keys.equals(other.keys)) {
            return false;
        }
        if (states == null) {
            if (other.states != null) {
                return false;
            }
        } else if (!states.equals(other.states)) {
            return false;
        }
        return true;
    }

}
