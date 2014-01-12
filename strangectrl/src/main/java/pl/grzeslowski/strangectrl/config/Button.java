package pl.grzeslowski.strangectrl.config;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;

public class Button {
    private String value;
    private final List<Key> keys = new ArrayList<>();
    private final List<State> states = new ArrayList<>();

    public Button() {
    }

    public Button(final String value, final Key... keys) {
        this.value = value;
        
        for (final Key key : keys) {
            this.keys.add(key);
        }
    }

    public synchronized String getValue() {
        return value;
    }

    public synchronized void setValue(final String value) {
        this.value = value;
    }

    public synchronized List<Key> getKeys() {
        return keys;
    }

    public synchronized List<State> getStates() {
        return states;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (keys == null ? 0 : keys.hashCode());
        result = prime * result + (states == null ? 0 : states.hashCode());
        result = prime * result + (value == null ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Button && equals((Button) obj);
    }

    private boolean equals(final Button obj) {
        if (obj == null) {
            return false;
        }

        return Objects.equal(value, obj.value)
                && Iterables.elementsEqual(keys, obj.keys)
                && Iterables.elementsEqual(states, obj.states);
    }

    @Override
    public String toString() {
        String join;
        if (keys != null && !keys.isEmpty()) {
            join = "K " + Joiner.on(", ").join(keys);
        } else {
            join = "S " + Joiner.on(", ").join(states);
        }
        return "Button[" + value + " | " + join + "]";
    }
}
