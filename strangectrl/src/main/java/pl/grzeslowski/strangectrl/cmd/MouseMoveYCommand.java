package pl.grzeslowski.strangectrl.cmd;

import com.xafero.strangectrl.awt.DesktopUtils;
import com.xafero.strangectrl.input.InputUtils;

public class MouseMoveYCommand extends MouseMoveCommand {

    public MouseMoveYCommand(final InputUtils inputUtils, final int maxMove,
            final DesktopUtils desktopUtils) {
        super(inputUtils, maxMove, desktopUtils);
    }

    public MouseMoveYCommand(final InputUtils inputUtils, final int maxMove,
            final double delta, final DesktopUtils desktopUtils) {
        super(inputUtils, maxMove, delta, desktopUtils);
    }

    @Override
    protected int moveX(final int x, final double value) {
        return x;
    }

    @Override
    protected int moveY(final int y, final double value) {
        return (int) Math.round(y + getMaxMove() * value);
    }

}
