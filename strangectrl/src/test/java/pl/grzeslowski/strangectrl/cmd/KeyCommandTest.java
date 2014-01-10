package pl.grzeslowski.strangectrl.cmd;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.GraphicsDevice;

import org.junit.Test;

import pl.grzeslowski.strangectrl.config.Key;

import com.xafero.strangectrl.input.InputUtils;

public class KeyCommandTest {

    @Test
    public void push_button_not_pushed_yet() throws Exception {

        // given
        final Key key = new Key("D");
        final InputUtils inputUtils = mock(InputUtils.class);
        final KeyCommand command = new KeyCommand(key, inputUtils);
        final GraphicsDevice dev = mock(GraphicsDevice.class);
        final double value = 1.0f;

        // when
        command.execute(dev, value);

        // then
        verify(inputUtils).pressKey(key);
    }

    @Test
    public void not_push_button_not_pushed_yet() throws Exception {

        // given
        final Key key = new Key("D");
        final InputUtils inputUtils = mock(InputUtils.class);
        final KeyCommand command = new KeyCommand(key, inputUtils);
        final GraphicsDevice dev = mock(GraphicsDevice.class);
        final double value = 0.0f;

        // when
        command.execute(dev, value);

        // then
        verify(inputUtils, never()).pressKey(key);
    }
    
    @Test
    public void push_button_only_once() throws Exception {

        // given
        final Key key = new Key("D");
        final InputUtils inputUtils = mock(InputUtils.class);
        final KeyCommand command = new KeyCommand(key, inputUtils);
        final GraphicsDevice dev = mock(GraphicsDevice.class);
        final double value = 1.0f;

        // when
        command.execute(dev, value);
        command.execute(dev, value);

        // then
        verify(inputUtils, times(1)).pressKey(key);
    }
}
