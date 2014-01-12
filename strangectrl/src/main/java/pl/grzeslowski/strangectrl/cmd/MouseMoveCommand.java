package pl.grzeslowski.strangectrl.cmd;

import java.awt.GraphicsDevice;
import java.awt.Point;

import com.xafero.strangectrl.awt.DesktopUtils;
import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.InputUtils;

public abstract class MouseMoveCommand extends AnalogCommand implements ICommand {


    public MouseMoveCommand(final InputUtils inputUtils, final int maxMove, final double delta) {
        super(inputUtils, maxMove, delta);
    }

    public MouseMoveCommand(final InputUtils inputUtils, final int maxMove) {
        super(inputUtils, maxMove);
    }

    @Override
    public void execute(final GraphicsDevice graphicsDevice, final double value) {
        if (getDelta()< Math.abs(value)) {
            final Point mousePoint = mousePosition(graphicsDevice);

            final int newX = moveX(mousePoint.x, value);
            final int newY = moveY(mousePoint.y, value);

            getInputUtils().moveMouse(new Point(newX, newY));
        }
    }

    @Override
    public boolean isPeriodCommand() {
        return true;
    }
    
    protected abstract int moveX(final int x, final double value);

    protected abstract int moveY(final int y, final double value);

    private Point mousePosition(final GraphicsDevice graphicsDevice) {
        return DesktopUtils.getMousePos(graphicsDevice);
    }
}
