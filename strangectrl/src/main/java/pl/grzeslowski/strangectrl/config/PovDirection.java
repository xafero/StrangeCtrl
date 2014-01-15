package pl.grzeslowski.strangectrl.config;

import java.util.ArrayList;
import java.util.List;

public abstract class PovDirection {
    protected final List<Key> keys = new ArrayList<>();
    protected final List<State> states = new ArrayList<>();

    public abstract List<Key> getKeys();

    public abstract String getIdentifier();

}
