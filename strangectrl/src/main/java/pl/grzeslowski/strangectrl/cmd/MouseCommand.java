package pl.grzeslowski.strangectrl.cmd;

import java.awt.GraphicsDevice;

import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.InputUtils;
import com.xafero.strangectrl.input.InputUtils.MouseButton;

public class MouseCommand implements ICommand {

    private final MouseButton mouseButton;
    private final InputUtils inputUtils;

    public MouseCommand(final MouseButton mouseButton, final InputUtils inputUtils) {
        this.mouseButton = mouseButton;
        this.inputUtils = inputUtils;
    }

    @Override
    public void execute(final GraphicsDevice graphicsDevice, final double value) {

    }

}
