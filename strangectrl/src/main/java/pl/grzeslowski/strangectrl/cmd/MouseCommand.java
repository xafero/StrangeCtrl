package pl.grzeslowski.strangectrl.cmd;

import java.awt.GraphicsDevice;

import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.InputUtils;
import com.xafero.strangectrl.input.InputUtils.MouseButton;

public class MouseCommand implements ICommand {

    private final MouseButton mouseButton;
    private final InputUtils inputUtils;

    public MouseCommand(final MouseButton mouseButton,
            final InputUtils inputUtils) {
        this.mouseButton = mouseButton;
        this.inputUtils = inputUtils;
    }

    @Override
    public void execute(final GraphicsDevice graphicsDevice, final double value) {
        if (value >= 0.5f) {
            System.out.println("pressed mouse button " + mouseButton);
            inputUtils.mousePress(mouseButton);
        } else {
            System.out.println("released mouse button " + mouseButton);
            inputUtils.mouseRelease(mouseButton);
        }
    }

    @Override
    public boolean isPeriodCommand() {
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + (mouseButton == null ? 0 : mouseButton.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MouseCommand other = (MouseCommand) obj;
        if (mouseButton != other.mouseButton) {
            return false;
        }
        return true;
    }

}
