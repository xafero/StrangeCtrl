package pl.grzeslowski.strangectrl.cmd;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.GraphicsDevice;
import java.util.ArrayList;
import java.util.List;

import pl.grzeslowski.strangectrl.config.Key;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.InputUtils;

public abstract class KeyCommand implements ICommand {
    final List<Key> keys;
    final InputUtils inputUtils;

    public KeyCommand(final Key key, final InputUtils inputUtils) {
        this(Lists.newArrayList(checkNotNull(key)), checkNotNull(inputUtils));
    }

    public KeyCommand(final List<Key> keys, final InputUtils inputUtils) {
        this.keys = new ArrayList<>(keys);
        this.inputUtils = checkNotNull(inputUtils);
    }

    public KeyCommand(final KeyCommand keyCommand) {
        keys = new ArrayList<>(keyCommand.keys);
        inputUtils = keyCommand.inputUtils;
    }

    @Override
    public void executePeriodCommand(final GraphicsDevice graphicsDevice,
            final double value) {
        execute(graphicsDevice, 1.0);
        execute(graphicsDevice, 0.0);
    }

    @Override
    public boolean isPeriodCommand() {
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(keys);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null && equal(getClass(), obj.getClass())) {
            final KeyCommand keyCommand = (KeyCommand) obj;

            return equal(keys, keyCommand.keys);
        } else {
            return false;
        }
    }
}
