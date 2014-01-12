package pl.grzeslowski.strangectrl.cmd;

import com.xafero.strangectrl.input.InputUtils;

public abstract class AnalogCommand {
    private final InputUtils inputUtils;
    private final int maxMove;
    private final double delta;

    public AnalogCommand(final InputUtils inputUtils, final int maxMove) {
        this(inputUtils, maxMove, 0.0f);
    }

    public AnalogCommand(final InputUtils inputUtils, final int maxMove,
            final double delta) {
        this.inputUtils = inputUtils;
        this.maxMove = maxMove;
        this.delta = delta;
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
    
    
}
