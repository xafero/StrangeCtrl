package pl.grzeslowski.strangectrl.cmd;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;

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

    @Test(expected = NullPointerException.class)
    public void null_conf() throws Exception {

        // given
        final CommandFactory commandFactory = new CommandFactory(
                mock(InputUtils.class));

        // when
        commandFactory.loadCommands(null);
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

        // when
        commandFactory.loadCommands(configuration);

        // then
        final ICommand keyCommand = commandFactory.getCommand("A");

        assertThat(keyCommand).isEqualTo(expected);
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
        final KeyCommand expected1 = new KeyCommand(key1, inputUtils);
        final KeyCommand expected2 = new KeyCommand(Lists.newArrayList(key1,
                key2),
                inputUtils);

        // when
        commandFactory.loadCommands(configuration);

        // then
        final ICommand keyCommand1 = commandFactory.getCommand("A");
        final ICommand keyCommand2 = commandFactory.getCommand("C");

        assertThat(keyCommand1).isEqualTo(expected1);
        assertThat(keyCommand2).isEqualTo(expected2);
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
        final KeyCommand nExpected = new KeyCommand(nKeys, inputUtils);
        final KeyCommand sExpected = new KeyCommand(sKeys, inputUtils);
        final KeyCommand eExpected = new KeyCommand(eKeys, inputUtils);
        final KeyCommand wExpected = new KeyCommand(wKeys, inputUtils);

        // when
        commandFactory.loadCommands(configuration);

        // then
        final ICommand keyCommandN = commandFactory.getCommand(n.getIdentifier());
        final ICommand keyCommandS = commandFactory.getCommand(s.getIdentifier());
        final ICommand keyCommandE = commandFactory.getCommand(e.getIdentifier());
        final ICommand keyCommandW = commandFactory.getCommand(w.getIdentifier());

        assertThat(keyCommandN).isEqualTo(nExpected);
        assertThat(keyCommandS).isEqualTo(sExpected);
        assertThat(keyCommandE).isEqualTo(eExpected);
        assertThat(keyCommandW).isEqualTo(wExpected);
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
        final KeyCommand nExpected = new KeyCommand(nKeys, inputUtils);
        final KeyCommand sExpected = new KeyCommand(sKeys, inputUtils);
        final KeyCommand eExpected = new KeyCommand(eKeys, inputUtils);
        final KeyCommand wExpected = new KeyCommand(wKeys, inputUtils);
        final KeyCommand neExpected = new KeyCommand(neKeys, inputUtils);
        final KeyCommand nwExpected = new KeyCommand(nwKeys, inputUtils);
        final KeyCommand seExpected = new KeyCommand(seKeys, inputUtils);
        final KeyCommand swExpected = new KeyCommand(swKeys, inputUtils);
        
        
        // when
      commandFactory.loadCommands(configuration);

        // then

        final ICommand keyCommandN = commandFactory.getCommand(n.getIdentifier());
        final ICommand keyCommandS = commandFactory.getCommand(s.getIdentifier());
        final ICommand keyCommandE = commandFactory.getCommand(e.getIdentifier());
        final ICommand keyCommandW = commandFactory.getCommand(w.getIdentifier());
        final ICommand keyCommandNE = commandFactory.getCommand(ne.getIdentifier());
        final ICommand keyCommandNW = commandFactory.getCommand(nw.getIdentifier());
        final ICommand keyCommandSE = commandFactory.getCommand(se.getIdentifier());
        final ICommand keyCommandSW = commandFactory.getCommand(sw.getIdentifier());

        assertThat(keyCommandN).isEqualTo(nExpected);
        assertThat(keyCommandS).isEqualTo(sExpected);
        assertThat(keyCommandE).isEqualTo(eExpected);
        assertThat(keyCommandW).isEqualTo(wExpected);
        assertThat(keyCommandNE).isEqualTo(neExpected);
        assertThat(keyCommandNW).isEqualTo(nwExpected);
        assertThat(keyCommandSE).isEqualTo(seExpected);
        assertThat(keyCommandSW).isEqualTo(swExpected);
    }

}
