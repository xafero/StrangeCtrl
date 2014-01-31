package pl.grzeslowski.strangectrl.cmd;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.GraphicsDevice;
import java.util.ArrayList;

import org.junit.Test;

import pl.grzeslowski.strangectrl.config.Key;

import com.google.common.collect.Lists;
import com.xafero.strangectrl.input.InputUtils;

public class ComboKeyCommandTest {

	@Test
	public void push_button_not_pushed_yet() throws Exception {

		// given
		final Key key = new Key("D");
		final InputUtils inputUtils = mock(InputUtils.class);
		final ComboKeyCommand command = new ComboKeyCommand(key, inputUtils);
		final GraphicsDevice dev = mock(GraphicsDevice.class);
		final double value = 1.0f;

		// when
		command.execute(dev, value);

		// then
		verify(inputUtils).pressKey(Lists.newArrayList(key));
	}


	@Test
	public void push_button_release_push() throws Exception {

		// given
		final Key key = new Key("D");
		final InputUtils inputUtils = mock(InputUtils.class);
		final ComboKeyCommand command = new ComboKeyCommand(key, inputUtils);
		final GraphicsDevice dev = mock(GraphicsDevice.class);

		// when
		command.execute(dev, 1.0f);
		command.execute(dev, 0.0f);
		command.execute(dev, 1.0f);

		// then
		verify(inputUtils, times(2)).pressKey(Lists.newArrayList(key));
		verify(inputUtils, times(1)).releaseKey(Lists.newArrayList(key));
	}

	@Test
	public void pushing_multiple_keys() throws Exception {

		// given
		final Key keyD = new Key("D");
		final Key keyE = new Key("e");
		final ArrayList<Key> keys = Lists.newArrayList(keyD,keyE);
		final InputUtils inputUtils = mock(InputUtils.class);
		final ComboKeyCommand command = new ComboKeyCommand(keys, inputUtils);
		final GraphicsDevice dev = mock(GraphicsDevice.class);

		// when
		command.execute(dev, 1.0f);

		// then

		verify(inputUtils).pressKey(keys);
	}
}
