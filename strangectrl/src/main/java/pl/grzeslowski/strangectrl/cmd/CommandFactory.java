package pl.grzeslowski.strangectrl.cmd;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.grzeslowski.strangectrl.config.Button;
import pl.grzeslowski.strangectrl.config.Configuration;

import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.InputUtils;

public class CommandFactory {

    private final InputUtils inputUtils;

    public CommandFactory(final InputUtils inputUtils) {
        this.inputUtils = checkNotNull(inputUtils);
    }

    public Set<ICommand> getCommands(final Configuration configuration) {
        final Set<ICommand> hashSet = new HashSet<>();

        final List<Button> buttons = configuration.getButtons();
        for (final Button button : buttons) {
            final KeyCommand keyCommand = new KeyCommand(button.getKeys(), inputUtils);

            hashSet.add(keyCommand);
        }

        return hashSet;
    }
}
