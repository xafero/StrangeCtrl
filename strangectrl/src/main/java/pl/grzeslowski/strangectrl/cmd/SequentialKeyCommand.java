package pl.grzeslowski.strangectrl.cmd;

import java.awt.GraphicsDevice;
import java.util.List;

import pl.grzeslowski.strangectrl.config.Key;

import com.xafero.strangectrl.input.InputUtils;

public class SequentialKeyCommand extends KeyCommand {

	private static final long WAIT = 0;

	public SequentialKeyCommand(final Key key, final InputUtils inputUtils) {
		super(key, inputUtils);
	}

	public SequentialKeyCommand(final List<Key> keys,
			final InputUtils inputUtils) {
		super(keys, inputUtils);
	}

	@Override
	public void execute(final GraphicsDevice graphicsDevice, final double value) {
		if (value >= 0.5) {
			for (final Key key : keys) {
				inputUtils.pressKey(key);
				sleep();
				inputUtils.releaseKey(key);
			}
		}
	}

	private void sleep() {
		try {
			Thread.sleep(WAIT);
		} catch (final InterruptedException e) {
			// not need to handle
		}
	}

}
