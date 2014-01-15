package pl.grzeslowski.strangectrl.config;

import java.util.List;

import com.google.common.collect.Lists;

public class NorthWestPov extends PovDirection {
    private NorthWestPov() {
    }

    public NorthWestPov(final Key... keys) {
        this(Lists.newArrayList(keys));
    }

    public NorthWestPov(final List<Key> keys) {
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
        return "NWP";
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
        final NorthWestPov other = (NorthWestPov) obj;
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
