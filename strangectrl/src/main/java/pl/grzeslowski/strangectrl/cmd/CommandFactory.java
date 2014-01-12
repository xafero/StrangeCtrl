package pl.grzeslowski.strangectrl.cmd;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.grzeslowski.strangectrl.config.Button;
import pl.grzeslowski.strangectrl.config.Configuration;
import pl.grzeslowski.strangectrl.config.EastPov;
import pl.grzeslowski.strangectrl.config.Key;
import pl.grzeslowski.strangectrl.config.NorthEastPov;
import pl.grzeslowski.strangectrl.config.NorthPov;
import pl.grzeslowski.strangectrl.config.NorthWestPov;
import pl.grzeslowski.strangectrl.config.Pov;
import pl.grzeslowski.strangectrl.config.PovDirection;
import pl.grzeslowski.strangectrl.config.SouthEastPov;
import pl.grzeslowski.strangectrl.config.SouthPov;
import pl.grzeslowski.strangectrl.config.SouthWestPov;
import pl.grzeslowski.strangectrl.config.WestPov;

import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.InputUtils;
import com.xafero.strangectrl.input.InputUtils.MouseButton;

public class CommandFactory {

    private final InputUtils inputUtils;
    private final Map<String, ICommand> commands = new HashMap<>();
    private final int maxMouseMove = 10;

    public CommandFactory(final InputUtils inputUtils,
            final Configuration configuration) {
        this.inputUtils = checkNotNull(inputUtils);
        loadCommands(checkNotNull(configuration));
        loadAnalogCommands();
    }

    private void loadCommands(final Configuration configuration) {

        // buttons
        final List<Button> buttons = configuration.getButtons();
        for (final Button button : buttons) {
            final List<Key> keys = button.getKeys();
            final ICommand command = createCommand(keys);

            commands.put(button.getValue(), command);
        }

        // pov
        final Pov pov = configuration.getPov();
        if (pov != null) {
            final NorthPov northPov = pov.getNorthPov();
            final SouthPov southPov = pov.getSouthPov();
            final EastPov eastPov = pov.getEastPov();
            final WestPov westPov = pov.getWestPov();
            final NorthEastPov northEastPov = pov.getNorthEastPov();
            final NorthWestPov northWestPov = pov.getNorthWestPov();
            final SouthEastPov southEastPov = pov.getSouthEastPov();
            final SouthWestPov southWestPov = pov.getSouthWestPov();

            putPovDirection(northPov);
            putPovDirection(southPov);
            putPovDirection(eastPov);
            putPovDirection(westPov);
            putPovDirection(northEastPov);
            putPovDirection(northWestPov);
            putPovDirection(southEastPov);
            putPovDirection(southWestPov);
        }
    }

    private void loadAnalogCommands() {

        // moving mouse
        final MouseMoveCommand mouseMoveXCommand = new MouseMoveXCommand(
                inputUtils, maxMouseMove, 0.1f);
        final MouseMoveCommand mouseMoveYCommand = new MouseMoveYCommand(
                inputUtils, maxMouseMove, 0.1f);
        commands.put("x", mouseMoveXCommand);
        commands.put("y", mouseMoveYCommand);
    }

    private ICommand createCommand(final List<Key> keys) {
        if (keys.size() == 1) {
            final Key key = keys.get(0);

            switch (key.getKey()) {
            case "LEFT_MOUSE":
                return new MouseCommand(MouseButton.LEFT, inputUtils);
            case "RIGHT_MOUSE":
                return new MouseCommand(MouseButton.RIGHT, inputUtils);
            case "CENTER_MOUSE":
                return new MouseCommand(MouseButton.CENTER, inputUtils);
            }
        }

        return new KeyCommand(keys, inputUtils);
    }

    private void putPovDirection(final PovDirection povDirection) {
        if (povDirection != null) {
            commands.put(povDirection.getIdentifier(),
                    createCommand(povDirection.getKeys()));
        }
    }

    public ICommand getCommand(final String identifier) {
        return commands.get(identifier);
    }
}
