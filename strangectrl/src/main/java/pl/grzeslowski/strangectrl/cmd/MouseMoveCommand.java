package pl.grzeslowski.strangectrl.cmd;

import java.awt.GraphicsDevice;
import java.awt.Point;

import com.xafero.strangectrl.awt.DesktopUtils;
import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.InputUtils;

public abstract class MouseMoveCommand implements ICommand {

    private final InputUtils inputUtils;
    private final int maxMove;
    private final double delta;

    public MouseMoveCommand(final InputUtils inputUtils, final int maxMove) {
        this(inputUtils, maxMove, 0.0f);
    }

    public MouseMoveCommand(final InputUtils inputUtils, final int maxMove,
            final double delta) {
        this.inputUtils = inputUtils;
        this.maxMove = maxMove;
        this.delta = delta;
    }

    @Override
    public void execute(final GraphicsDevice graphicsDevice, final double value) {
        if (delta < Math.abs(value)) {
            final Point mousePoint = mousePosition(graphicsDevice);

            final int newX = moveX(mousePoint.x, value);
            final int newY = moveY(mousePoint.y, value);

            inputUtils.moveMouse(new Point(newX, newY));
        }
    }

    protected abstract int moveX(final int x, final double value);

    protected abstract int moveY(final int y, final double value);

    private Point mousePosition(final GraphicsDevice graphicsDevice) {
        return DesktopUtils.getMousePos(graphicsDevice);
    }

    protected int getMaxMove() {
        return maxMove;
    }

}
