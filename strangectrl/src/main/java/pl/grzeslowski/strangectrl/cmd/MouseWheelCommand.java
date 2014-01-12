package pl.grzeslowski.strangectrl.cmd;

import java.awt.GraphicsDevice;

import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.InputUtils;

public class MouseWheelCommand extends AnalogCommand implements ICommand {

    public MouseWheelCommand(final InputUtils inputUtils, final int maxMove,
            final double delta) {
        super(inputUtils, maxMove, delta);
    }

    public MouseWheelCommand(final InputUtils inputUtils, final int maxMove) {
        super(inputUtils, maxMove);
    }

    @Override
    public void execute(final GraphicsDevice graphicsDevice, final double value) {
        if (getDelta() < Math.abs(value)) {
            getInputUtils().mouseWheel((int) Math.round(value * getMaxMove()));
        }
    }

    @Override
    public boolean isPeriodCommand() {
        return true;
    }

}
