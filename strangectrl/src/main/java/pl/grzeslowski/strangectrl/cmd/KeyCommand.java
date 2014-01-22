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
		return Objects.hashCode(keys);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof KeyCommand) {
			final KeyCommand keyCommand = (KeyCommand) obj;

			return equal(keys, keyCommand.keys);
		} else {
			return false;
		}
	}

}
