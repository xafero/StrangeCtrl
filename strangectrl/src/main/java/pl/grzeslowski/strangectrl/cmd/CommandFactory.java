package pl.grzeslowski.strangectrl.cmd;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.grzeslowski.strangectrl.config.Button;
import pl.grzeslowski.strangectrl.config.Configuration;
import pl.grzeslowski.strangectrl.config.EastPov;
import pl.grzeslowski.strangectrl.config.NorthEastPov;
import pl.grzeslowski.strangectrl.config.NorthPov;
import pl.grzeslowski.strangectrl.config.NorthWestPov;
import pl.grzeslowski.strangectrl.config.Pov;
import pl.grzeslowski.strangectrl.config.SouthEastPov;
import pl.grzeslowski.strangectrl.config.SouthPov;
import pl.grzeslowski.strangectrl.config.SouthWestPov;
import pl.grzeslowski.strangectrl.config.WestPov;

import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.InputUtils;

public class CommandFactory {

    private final InputUtils inputUtils;

    public CommandFactory(final InputUtils inputUtils) {
        this.inputUtils = checkNotNull(inputUtils);
    }

    public Set<ICommand> getCommands(final Configuration configuration) {
        final Set<ICommand> hashSet = new HashSet<>();

        // buttons
        final List<Button> buttons = configuration.getButtons();
        for (final Button button : buttons) {
            final KeyCommand keyCommand = new KeyCommand(button.getKeys(),
                    inputUtils);

            hashSet.add(keyCommand);
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

            if (northPov != null) {
                hashSet.add(new KeyCommand(northPov.getKeys(), inputUtils));
            }

            if (southPov != null) {
                hashSet.add(new KeyCommand(southPov.getKeys(), inputUtils));
            }

            if (eastPov != null) {
                hashSet.add(new KeyCommand(eastPov.getKeys(), inputUtils));
            }

            if (westPov != null) {
                hashSet.add(new KeyCommand(westPov.getKeys(), inputUtils));
            }

            if (northEastPov != null) {
                hashSet.add(new KeyCommand(northEastPov.getKeys(), inputUtils));
            }

            if (northWestPov != null) {
                hashSet.add(new KeyCommand(northWestPov.getKeys(), inputUtils));
            }

            if (southEastPov != null) {
                hashSet.add(new KeyCommand(southEastPov.getKeys(), inputUtils));
            }

            if (southWestPov != null) {
                hashSet.add(new KeyCommand(southWestPov.getKeys(), inputUtils));
            }
        }

        return hashSet;
    }
}
