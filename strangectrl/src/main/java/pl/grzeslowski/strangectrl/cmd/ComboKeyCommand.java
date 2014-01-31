package pl.grzeslowski.strangectrl.cmd;

import java.awt.GraphicsDevice;
import java.util.List;

import pl.grzeslowski.strangectrl.config.Key;

import com.xafero.strangectrl.input.InputUtils;

public class ComboKeyCommand extends KeyCommand {

	public ComboKeyCommand(final Key key, final InputUtils inputUtils) {
		super(key, inputUtils);
	}

	public ComboKeyCommand(final List<Key> keys, final InputUtils inputUtils) {
		super(keys, inputUtils);
	}

	@Override
	public void execute(final GraphicsDevice dev, final double value) {
		if (value >= 0.5f) {
			inputUtils.pressKey(keys);
		} else {
			inputUtils.releaseKey(keys);
		}
	}
}
