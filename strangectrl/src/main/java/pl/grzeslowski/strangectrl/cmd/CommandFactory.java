package pl.grzeslowski.strangectrl.cmd;

import java.util.HashSet;
import java.util.Set;

import pl.grzeslowski.strangectrl.config.Configuration;

import com.xafero.strangectrl.cmd.ICommand;

public class CommandFactory {

    public Set<ICommand> getCommands(final Configuration configuration) {
        return new HashSet<>();
    }
}
