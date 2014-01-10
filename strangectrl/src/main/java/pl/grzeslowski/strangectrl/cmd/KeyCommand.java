package pl.grzeslowski.strangectrl.cmd;

import java.awt.GraphicsDevice;

import pl.grzeslowski.strangectrl.config.Key;

import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.InputUtils;

public class KeyCommand implements ICommand {

    private final Key key;
    private final InputUtils inputUtils;

    public KeyCommand(final Key key, final InputUtils inputUtils) {
        this.key = key;
        this.inputUtils = inputUtils;
    }

    @Override
    public void execute(final GraphicsDevice dev, final double value) {
        if (value >= 0.5f) {
            inputUtils.pressKey(key);
        }
    }

}
