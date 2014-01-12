package pl.grzeslowski.strangectrl.cmd;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.GraphicsDevice;
import java.util.ArrayList;
import java.util.List;

import pl.grzeslowski.strangectrl.config.Key;

import com.google.common.collect.Lists;
import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.InputUtils;

public class KeyCommand implements ICommand {

    private final List<Key> keys;
    private final InputUtils inputUtils;

    public KeyCommand(final Key key, final InputUtils inputUtils) {
        this(Lists.newArrayList(key), inputUtils);
    }

    public KeyCommand(final List<Key> keys, final InputUtils inputUtils) {
        this.keys = new ArrayList<>(keys);
        this.inputUtils = checkNotNull(inputUtils);
    }

    @Override
    public void execute(final GraphicsDevice dev, final double value) {
        if (value >= 0.5f) {
            inputUtils.pressKey(keys);
        } else {
            inputUtils.releaseKey(keys);
        }
    }

    @Override
    public boolean isPeriodCommand() {
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (keys == null ? 0 : keys.hashCode());
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
        final KeyCommand other = (KeyCommand) obj;
        if (keys == null) {
            if (other.keys != null) {
                return false;
            }
        } else if (!keys.equals(other.keys)) {
            return false;
        }
        return true;
    }

}
