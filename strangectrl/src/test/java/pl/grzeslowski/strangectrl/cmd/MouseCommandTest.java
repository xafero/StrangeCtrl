package pl.grzeslowski.strangectrl.cmd;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.xafero.strangectrl.input.InputUtils;
import com.xafero.strangectrl.input.InputUtils.MouseButton;

public class MouseCommandTest {
    @Test
    public void push_once_mouse() throws Exception {

        // given
        final InputUtils inputUtils = mock(InputUtils.class);
        final MouseButton button = MouseButton.LEFT;
        final MouseCommand mouseCommand = new MouseCommand(button,
                inputUtils);

        // when
        mouseCommand.execute(null, 1.0f);

        // then
        verify(inputUtils).mousePress(button);
    }
}
