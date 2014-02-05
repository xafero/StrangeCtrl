package pl.grzeslowski.strangectrl.cmd;

import java.util.Objects;

import com.xafero.strangectrl.input.InputUtils;

public abstract class AnalogCommand {
    private final InputUtils inputUtils;
    private final int maxMove;
    private final double delta;
    private double lastvalue;

    public AnalogCommand(final InputUtils inputUtils, final int maxMove) {
        this(inputUtils, maxMove, 0.0f);
    }

    public AnalogCommand(final InputUtils inputUtils, final int maxMove,
            final double delta) {
        this.inputUtils = inputUtils;
        this.maxMove = maxMove;
        this.delta = delta;
    }

    protected boolean canExecute(final double value) {
        final double abs = Math.abs(value);

        final boolean can = abs >= lastvalue;
        lastvalue = abs;

        return can;
    }

    protected InputUtils getInputUtils() {
        return inputUtils;
    }

    protected int getMaxMove() {
        return maxMove;
    }

    protected double getDelta() {
        return delta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxMove, delta);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null && this.getClass().equals(obj.getClass())) {
            final AnalogCommand analogCommand = (AnalogCommand) obj;

            return maxMove == analogCommand.maxMove
                    && delta == analogCommand.delta;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("%s[maxMove = %s, delta = %s]", getClass()
                .getSimpleName(), maxMove, delta);
    }
}
