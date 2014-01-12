package pl.grzeslowski.strangectrl.cmd;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import pl.grzeslowski.strangectrl.config.Button;
import pl.grzeslowski.strangectrl.config.Configuration;
import pl.grzeslowski.strangectrl.config.EastPov;
import pl.grzeslowski.strangectrl.config.Key;
import pl.grzeslowski.strangectrl.config.NorthEastPov;
import pl.grzeslowski.strangectrl.config.NorthPov;
import pl.grzeslowski.strangectrl.config.NorthWestPov;
import pl.grzeslowski.strangectrl.config.Pov;
import pl.grzeslowski.strangectrl.config.SouthEastPov;
import pl.grzeslowski.strangectrl.config.SouthPov;
import pl.grzeslowski.strangectrl.config.SouthWestPov;
import pl.grzeslowski.strangectrl.config.WestPov;

import com.google.common.collect.Lists;
import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.InputUtils;

public class CommandFactoryTest {
    @Test
    public void get_commands_from_empty_conf() throws Exception {

        // given
        final Configuration configuration = new Configuration();
        final CommandFactory commandFactory = new CommandFactory(
                mock(InputUtils.class));

        // expected
        final int size = 0;

        // when
        final Set<ICommand> commands = commandFactory
                .getCommands(configuration);

        // then
        assertThat(commands).hasSize(size);
    }

    @Test(expected = NullPointerException.class)
    public void null_conf() throws Exception {

        // given
        final CommandFactory commandFactory = new CommandFactory(
                mock(InputUtils.class));

        // when
        commandFactory.getCommands(null);
    }

    @Test
    public void get_commands_one_button() throws Exception {

        // given
        final Key key = new Key("Q");
        final Button button = new Button("A", key);
        final Configuration configuration = new Configuration(button);
        final InputUtils inputUtils = mock(InputUtils.class);
        final CommandFactory commandFactory = new CommandFactory(inputUtils);

        // expected
        final KeyCommand expected = new KeyCommand(key, inputUtils);
        final int size = 1;

        // when
        final Set<ICommand> commands = commandFactory
                .getCommands(configuration);

        // then
        assertThat(commands).hasSize(size);
        final ICommand iCommand = commands.iterator().next();
        assertThat(iCommand.getClass()).isEqualTo(expected.getClass());
        final KeyCommand keyCommand = (KeyCommand) iCommand;
        keyCommand.execute(null, 1.0f);
        verify(inputUtils).pressKey(Lists.newArrayList(key));
    }

    @Test
    public void get_commands_two_buttons() throws Exception {

        // given
        final Key key1 = new Key("Q");
        final Button button1 = new Button("A", key1);

        final Key key2 = new Key("W");
        final Button button2 = new Button("C", key1, key2);

        final Configuration configuration = new Configuration(button1, button2);

        final InputUtils inputUtils = mock(InputUtils.class);
        final CommandFactory commandFactory = new CommandFactory(inputUtils);

        // expected
        final int size = 2;

        // when
        final Set<ICommand> commands = commandFactory
                .getCommands(configuration);

        // then
        assertThat(commands).hasSize(size);

        final Iterator<ICommand> it = commands.iterator();
        final ICommand iCommand1 = it.next();
        final ICommand iCommand2 = it.next();

        assertThat(iCommand1.getClass()).isEqualTo(KeyCommand.class);
        assertThat(iCommand2.getClass()).isEqualTo(KeyCommand.class);

        final KeyCommand keyCommand1 = (KeyCommand) iCommand1;
        final KeyCommand keyCommand2 = (KeyCommand) iCommand2;

        keyCommand1.execute(null, 1.0f);
        keyCommand2.execute(null, 1.0f);

        verify(inputUtils).pressKey(Lists.newArrayList(key1));
        verify(inputUtils).pressKey(Lists.newArrayList(key1, key2));
    }

    @Test
    public void pov_in_conf_4() throws Exception {

        final List<Key> nKeys = Lists.newArrayList(new Key("w"), new Key("d"),
                new Key("r"));
        final List<Key> sKeys = Lists.newArrayList(new Key("h"), new Key("a"),
                new Key("d"));
        final List<Key> eKeys = Lists.newArrayList(new Key("p"), new Key("c"),
                new Key("g"));
        final List<Key> wKeys = Lists.newArrayList(new Key("z"), new Key("j"),
                new Key("h"));

        final NorthPov n = new NorthPov(nKeys);
        final SouthPov s = new SouthPov(sKeys);
        final EastPov e = new EastPov(eKeys);
        final WestPov w = new WestPov(wKeys);
        // given
        final Pov pov = new Pov(n, s, e, w);
        final Configuration configuration = new Configuration(pov);

        final InputUtils inputUtils = mock(InputUtils.class);
        final CommandFactory commandFactory = new CommandFactory(inputUtils);

        // expected
        final int size = 4;

        // when
        final Set<ICommand> commands = commandFactory
                .getCommands(configuration);

        // then
        assertThat(commands).hasSize(size);
        for (final ICommand iCommand : commands) {
            assertThat(iCommand.getClass()).isEqualTo(KeyCommand.class);
        }

        for (final ICommand iCommand : commands) {
            iCommand.execute(null, 1.0f);
        }

        verify(inputUtils).pressKey(nKeys);
        verify(inputUtils).pressKey(sKeys);
        verify(inputUtils).pressKey(eKeys);
        verify(inputUtils).pressKey(wKeys);
    }

    @Test
    public void pov_in_conf_8() throws Exception {

        final List<Key> nKeys = Lists.newArrayList(new Key("w"), new Key("d"),
                new Key("r"));
        final List<Key> sKeys = Lists.newArrayList(new Key("h"), new Key("a"),
                new Key("d"));
        final List<Key> eKeys = Lists.newArrayList(new Key("p"), new Key("c"),
                new Key("g"));
        final List<Key> wKeys = Lists.newArrayList(new Key("z"), new Key("j"),
                new Key("h"));

        final List<Key> neKeys = Lists.newArrayList(new Key("a"), new Key("t"),
                new Key("e"));
        final List<Key> nwKeys = Lists.newArrayList(new Key("d"), new Key("s"),
                new Key("s"));
        final List<Key> seKeys = Lists.newArrayList(new Key("f"), new Key("d"),
                new Key("d"));
        final List<Key> swKeys = Lists.newArrayList(new Key("g"), new Key("t"),
                new Key("a"));

        final NorthPov n = new NorthPov(nKeys);
        final SouthPov s = new SouthPov(sKeys);
        final EastPov e = new EastPov(eKeys);
        final WestPov w = new WestPov(wKeys);

        final NorthEastPov ne = new NorthEastPov(neKeys);
        final NorthWestPov nw = new NorthWestPov(nwKeys);
        final SouthEastPov se = new SouthEastPov(seKeys);
        final SouthWestPov sw = new SouthWestPov(swKeys);
        // given
        final Pov pov = new Pov(n, s, e, w, ne, nw, se, sw);
        final Configuration configuration = new Configuration(pov);

        final InputUtils inputUtils = mock(InputUtils.class);
        final CommandFactory commandFactory = new CommandFactory(inputUtils);

        // expected
        final int size = 8;

        // when
        final Set<ICommand> commands = commandFactory
                .getCommands(configuration);

        // then
        assertThat(commands).hasSize(size);
        for (final ICommand iCommand : commands) {
            assertThat(iCommand.getClass()).isEqualTo(KeyCommand.class);
        }

        for (final ICommand iCommand : commands) {
            iCommand.execute(null, 1.0f);
        }

        verify(inputUtils).pressKey(nKeys);
        verify(inputUtils).pressKey(sKeys);
        verify(inputUtils).pressKey(eKeys);
        verify(inputUtils).pressKey(wKeys);
        verify(inputUtils).pressKey(neKeys);
        verify(inputUtils).pressKey(nwKeys);
        verify(inputUtils).pressKey(seKeys);
        verify(inputUtils).pressKey(swKeys);
    }

}
