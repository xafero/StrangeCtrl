package pl.grzeslowski.strangectrl.cmd;
import java.awt.GraphicsDevice;
import java.util.List;

import pl.grzeslowski.strangectrl.config.Key;

import com.xafero.strangectrl.input.InputUtils;

public class PovKeyCommand extends KeyCommand {

	public PovKeyCommand(final Key key, final InputUtils inputUtils) {
		super(key, inputUtils);
	}

	public PovKeyCommand(final List<Key> keys, final InputUtils inputUtils) {
		super(keys, inputUtils);
	}

	@Override
	public void execute(final GraphicsDevice dev, final double value) {
		if (value != 0) {
			super.execute(dev, 1.0);
		} else {
			super.execute(dev, 0.0);
		}
	}
}
