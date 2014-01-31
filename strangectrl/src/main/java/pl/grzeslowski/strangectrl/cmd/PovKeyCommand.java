package pl.grzeslowski.strangectrl.cmd;

import static com.google.common.base.Objects.equal;

import java.awt.GraphicsDevice;
import java.util.Objects;

import com.xafero.strangectrl.cmd.ICommand;

public class PovKeyCommand implements ICommand {
    private final ICommand iCommand;

    public PovKeyCommand(final ICommand iCommand) {
        this.iCommand = iCommand;
    }

    @Override
    public void execute(final GraphicsDevice dev, final double value) {
        if (value != 0) {
            iCommand.execute(dev, 1.0);
        } else {
            iCommand.execute(dev, 0.0);
        }
    }

    @Override
    public void executePeriodCommand(final GraphicsDevice graphicsDevice,
            final double value) {
        iCommand.execute(graphicsDevice, 1.0);
        iCommand.execute(graphicsDevice, 0.0);
    }

    @Override
    public boolean isPeriodCommand() {
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(iCommand);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof PovKeyCommand) {
            final PovKeyCommand pov = (PovKeyCommand) obj;

            return equal(iCommand, pov.iCommand);
        } else {
            return false;
        }
    }
}
