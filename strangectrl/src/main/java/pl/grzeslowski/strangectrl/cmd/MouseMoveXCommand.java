package pl.grzeslowski.strangectrl.cmd;

import com.xafero.strangectrl.awt.DesktopUtils;
import com.xafero.strangectrl.input.InputUtils;

public class MouseMoveXCommand extends MouseMoveCommand {

    public MouseMoveXCommand(final InputUtils inputUtils, final int maxMove,
            final DesktopUtils desktopUtils) {
        super(inputUtils, maxMove, desktopUtils);
    }

    public MouseMoveXCommand(final InputUtils inputUtils, final int maxMove,
            final double delta,
            final DesktopUtils desktopUtils) {
        super(inputUtils, maxMove, delta, desktopUtils);
    }

    @Override
    protected int moveX(final int x, final double value) {
        return (int) Math.round(x + getMaxMove() * value);
    }

    @Override
    protected int moveY(final int y, final double value) {
        return y;
    }

}
