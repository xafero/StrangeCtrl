package pl.grzeslowski.strangectrl.cmd;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.GraphicsDevice;
import java.util.ArrayList;

import org.junit.Test;

import pl.grzeslowski.strangectrl.config.Key;

import com.google.common.collect.Lists;
import com.xafero.strangectrl.input.InputUtils;

public class SequentialKeyComboTest {
	@Test
	public void pushing_multiple_keys() throws Exception {

		// given
		final Key keyD = new Key("D");
		final Key keyE = new Key("e");
		final ArrayList<Key> keys = Lists.newArrayList(keyD, keyE);
		final InputUtils inputUtils = mock(InputUtils.class);
		final SequentialKeyCommand command = new SequentialKeyCommand(keys,
				inputUtils);
		final GraphicsDevice dev = mock(GraphicsDevice.class);

		// when
		command.execute(dev, 1.0f);

		// then
		verify(inputUtils).pressKey(keyD);
		verify(inputUtils).pressKey(keyE);

		verify(inputUtils).releaseKey(keyD);
		verify(inputUtils).releaseKey(keyE);
	}
}
