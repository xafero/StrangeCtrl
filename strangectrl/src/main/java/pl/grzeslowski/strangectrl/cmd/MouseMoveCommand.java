package pl.grzeslowski.strangectrl.cmd;

import java.awt.GraphicsDevice;
import java.awt.Point;

import com.xafero.strangectrl.awt.DesktopUtils;
import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.InputUtils;

public class MouseMoveCommand implements ICommand {

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
        if (delta < value) {
            final Point mousePoint = mousePosition(graphicsDevice);

            final double newX = mousePoint.x + maxMove * value;
            final double newY = mousePoint.y + maxMove * value;
            
            mousePoint.setLocation(newX,newY);
            inputUtils.moveMouse(mousePoint);
        }
    }

    private Point mousePosition(final GraphicsDevice graphicsDevice) {
        return DesktopUtils.getMousePos(graphicsDevice);
    }

}
