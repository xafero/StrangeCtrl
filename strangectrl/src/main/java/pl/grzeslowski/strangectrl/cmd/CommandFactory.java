package pl.grzeslowski.strangectrl.cmd;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.grzeslowski.strangectrl.config.Button;
import pl.grzeslowski.strangectrl.config.Configuration;
import pl.grzeslowski.strangectrl.config.Key;
import pl.grzeslowski.strangectrl.config.Pov;
import pl.grzeslowski.strangectrl.config.Setup;

import com.google.common.collect.Sets;
import com.xafero.strangectrl.awt.DesktopUtils;
import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.InputUtils;
import com.xafero.strangectrl.input.InputUtils.MouseButton;

public class CommandFactory {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(CommandFactory.class);
    private final static double DELTA_FOR_MOUSE_MOVE = 0.2;

    private final InputUtils inputUtils;
    private final Map<String, ICommand> commands = new HashMap<>();
    private final Set<CommandNameMapper> mappers;

    public CommandFactory(final InputUtils inputUtils,
            final Configuration configuration,
            final Set<? extends CommandNameMapper> mappers) {
        this.inputUtils = checkNotNull(inputUtils);
        this.mappers = new HashSet<CommandNameMapper>(checkNotNull(mappers));

        loadCommands(checkNotNull(configuration));
        loadAnalogCommands(configuration.getSetup());
    }

    public CommandFactory(final InputUtils inputUtils,
            final Configuration configuration) {
        this(inputUtils, configuration, new XboxNameMapper());
    }

    public CommandFactory(final InputUtils mock,
            final Configuration configuration, final CommandNameMapper mapper) {
        this(mock, configuration, Sets.newHashSet(mapper));
    }

    private void loadCommands(final Configuration configuration) {

        // buttons
        final List<Button> buttons = configuration.getButtons();
        for (final Button button : buttons) {
            final ICommand command = createCommand(button);

            commands.put(button.getValue(), command);
        }

        // pov
        final Pov pov = configuration.getPov();
        if (pov != null) {
            final Button northPov = pov.getNorthPov();
            final Button southPov = pov.getSouthPov();
            final Button eastPov = pov.getEastPov();
            final Button westPov = pov.getWestPov();
            final Button northEastPov = pov.getNorthEastPov();
            final Button northWestPov = pov.getNorthWestPov();
            final Button southEastPov = pov.getSouthEastPov();
            final Button southWestPov = pov.getSouthWestPov();

            putButton("NP", northPov);
            putButton("SP", southPov);
            putButton("EP", eastPov);
            putButton("WP", westPov);
            putButton("NEP", northEastPov);
            putButton("NWP", northWestPov);
            putButton("SEP", southEastPov);
            putButton("SWP", southWestPov);
        }
    }

    private void loadAnalogCommands(final Setup setup) {

        // moving mouse
        final DesktopUtils desktopUtils = new DesktopUtils();
        final MouseMoveCommand mouseMoveXCommand = new MouseMoveXCommand(
                inputUtils, setup.getMaxMouseMove(), DELTA_FOR_MOUSE_MOVE,
                desktopUtils);
        final MouseMoveCommand mouseMoveYCommand = new MouseMoveYCommand(
                inputUtils, setup.getMaxMouseMove(), DELTA_FOR_MOUSE_MOVE,
                desktopUtils);
        commands.put("x", mouseMoveXCommand);
        commands.put("y", mouseMoveYCommand);

        // mouse wheel
        final MouseWheelCommand mouseWheelCommand = new MouseWheelCommand(
                inputUtils, setup.getScrollLines(), 0.99);
        commands.put("ry", mouseWheelCommand);
    }

    private ICommand createCommand(final Button button) {
        final List<Key> keys = button.getKeys();

        if (equal(Button.COMBO_TYPE, button.getPressType())) {
            final MouseCommand mouseCommand = getMouseCommand(keys);

            if (mouseCommand == null) {
                return new ComboKeyCommand(keys, inputUtils);
            } else {
                return mouseCommand;
            }
        } else if (equal(Button.SEQUENTIAL_TYPE, button.getPressType())) {
            final MouseCommand mouseCommand = getMouseCommand(keys);

            if (mouseCommand == null) {
                return new SequentialKeyCommand(keys, inputUtils);
            } else {
                return mouseCommand;
            }
        } else {
            final String msg = "Don't know this press type ("
                    + button.getPressType() + ")";

            logger.error(msg);
            throw new RuntimeException(msg);
        }
    }

    private ICommand createPovCommand(final ICommand keyCommand) {
        return new PovKeyCommand(keyCommand);
    }

    private MouseCommand getMouseCommand(final List<Key> keys) {
        if (keys.size() == 1) {
            final Key key = keys.get(0);

            switch (key.getKey()) {
            case "LEFT_MOUSE":
                return new MouseCommand(MouseButton.LEFT, inputUtils);
            case "RIGHT_MOUSE":
                return new MouseCommand(MouseButton.RIGHT, inputUtils);
            case "CENTER_MOUSE":
                return new MouseCommand(MouseButton.CENTER, inputUtils);
            default:
                // go to the last return
            }
        }

        return null;
    }

    private void putButton(final String identifier, final Button povDirection) {
        if (povDirection != null) {
            commands.put(identifier,
                    createPovCommand(createCommand(povDirection)));
        }
    }

    public ICommand getCommand(final String identifier, final double value) {
        final ICommand iCommand = commands.get(identifier);
        if (iCommand != null) {
            return iCommand;
        } else {
            return getCommandFromMappers(identifier, value);
        }
    }

    private ICommand getCommandFromMappers(final String identifier,
            final double value) {
        for (final CommandNameMapper mapper : mappers) {
            if (mapper.canMap(identifier, value)) {
                final String mappedIdentifier = mapper.map(identifier, value);

                final ICommand command = commands.get(mappedIdentifier);
                if (command != null) {
                    return command;
                }
            }
        }

        // could not map anything
        return null;
    }
}
