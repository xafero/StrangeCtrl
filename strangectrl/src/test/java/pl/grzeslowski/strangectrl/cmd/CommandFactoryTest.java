package pl.grzeslowski.strangectrl.cmd;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Set;

import org.junit.Test;

import pl.grzeslowski.strangectrl.config.Configuration;

import com.xafero.strangectrl.cmd.ICommand;

public class CommandFactoryTest {
    @Test
    public void get_commands_from_empty_conf() throws Exception {

        // given
        final Configuration configuration = new Configuration();
        final CommandFactory commandFactory = new CommandFactory();

        // expected
        final int size = 0;

        // when
        final Set<ICommand> commands = commandFactory.getCommands(configuration);

        // then
        assertThat(commands).hasSize(size);
    }
}
