package pl.grzeslowski.strangectrl.config;

import java.util.List;

public class SouthWestPov {
    private List<Key> keys;
    private List<State> states;
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
        final SouthWestPov other = (SouthWestPov) obj;
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
