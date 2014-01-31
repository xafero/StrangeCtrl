package pl.grzeslowski.strangectrl.cmd;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Test;

import pl.grzeslowski.strangectrl.config.Button;
import pl.grzeslowski.strangectrl.config.Configuration;
import pl.grzeslowski.strangectrl.config.Key;
import pl.grzeslowski.strangectrl.config.Pov;

import com.google.common.collect.Lists;
import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.InputUtils;
import com.xafero.strangectrl.input.InputUtils.MouseButton;

public class CommandFactoryTest {

    @Test(expected = NullPointerException.class)
    public void null_conf() throws Exception {

        // given
        new CommandFactory(mock(InputUtils.class), null);

    }

    @Test
    public void get_commands_one_button() throws Exception {

        // given
        final Key key = new Key("Q");
        final Button button = new Button("A", key);
        final Configuration configuration = new Configuration(button);
        final InputUtils inputUtils = mock(InputUtils.class);

        // expected
        final KeyCommand expected = new ComboKeyCommand(key, inputUtils);

        // when
        final CommandFactory commandFactory = new CommandFactory(inputUtils,
                configuration);

        // then
        final ICommand keyCommand = commandFactory.getCommand("A", 0.0);

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

        // expected
        final KeyCommand expected1 = new ComboKeyCommand(key1, inputUtils);
        final KeyCommand expected2 = new ComboKeyCommand(Lists.newArrayList(
                key1, key2), inputUtils);

        // when
        final CommandFactory commandFactory = new CommandFactory(inputUtils,
                configuration);

        // then
        final ICommand keyCommand1 = commandFactory.getCommand("A", 0.0);
        final ICommand keyCommand2 = commandFactory.getCommand("C", 0.0);

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

        final Button n = new Button("NP", nKeys);
        final Button s = new Button("SP", sKeys);
        final Button e = new Button("EP", eKeys);
        final Button w = new Button("WP", wKeys);

        // given
        final Pov pov = new Pov.PovBuilder().northPov(n).southPov(s).eastPov(e)
                .westPov(w).build();
        final Configuration configuration = new Configuration(pov);

        final InputUtils inputUtils = mock(InputUtils.class);

        // expected
        // @formatter:off
        final PovKeyCommand nExpected = new PovKeyCommand(new SequentialKeyCommand(nKeys, inputUtils));
        final PovKeyCommand sExpected = new PovKeyCommand(new SequentialKeyCommand(sKeys, inputUtils));
        final PovKeyCommand eExpected = new PovKeyCommand(new SequentialKeyCommand(eKeys, inputUtils));
        final PovKeyCommand wExpected = new PovKeyCommand(new SequentialKeyCommand(wKeys, inputUtils));
        // @formatter:on

        // when
        final CommandFactory commandFactory = new CommandFactory(inputUtils,
                configuration);

        // then
        final ICommand keyCommandN = commandFactory.getCommand("NP", 0.0);
        final ICommand keyCommandS = commandFactory.getCommand("SP", 0.0);
        final ICommand keyCommandE = commandFactory.getCommand("EP", 0.0);
        final ICommand keyCommandW = commandFactory.getCommand("WP", 0.0);

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

        final Button n = new Button("NP", nKeys);
        final Button s = new Button("SP", sKeys);
        final Button e = new Button("EP", eKeys);
        final Button w = new Button("WP", wKeys);

        final Button ne = new Button("NEP", neKeys);
        final Button nw = new Button("NWP", nwKeys);
        final Button se = new Button("SEP", seKeys);
        final Button sw = new Button("SWP", swKeys);
        // given
        final Pov pov = new Pov.PovBuilder().northPov(n).southPov(s).eastPov(e)
                .westPov(w).northEastPov(ne).northWestPov(nw).southEastPov(se)
                .southWestPov(sw).build();
        final Configuration configuration = new Configuration(pov);

        final InputUtils inputUtils = mock(InputUtils.class);

        // expected
        // @formatter:off
        final ICommand nExpected = new PovKeyCommand(new SequentialKeyCommand(nKeys, inputUtils));
        final ICommand sExpected = new PovKeyCommand(new SequentialKeyCommand(sKeys, inputUtils));
        final ICommand eExpected = new PovKeyCommand(new SequentialKeyCommand(eKeys, inputUtils));
        final ICommand wExpected = new PovKeyCommand(new SequentialKeyCommand(wKeys, inputUtils));
        final ICommand neExpected = new PovKeyCommand(new SequentialKeyCommand(neKeys, inputUtils));
        final ICommand nwExpected = new PovKeyCommand(new SequentialKeyCommand(nwKeys, inputUtils));
        final ICommand seExpected = new PovKeyCommand(new SequentialKeyCommand(seKeys, inputUtils));
        final ICommand swExpected = new PovKeyCommand(new SequentialKeyCommand(swKeys, inputUtils));
        // @formatter:on

        // when
        final CommandFactory commandFactory = new CommandFactory(inputUtils,
                configuration);

        // then

        final ICommand keyCommandN = commandFactory.getCommand("NP", 0.0);
        final ICommand keyCommandS = commandFactory.getCommand("SP", 0.0);
        final ICommand keyCommandE = commandFactory.getCommand("EP", 0.0);
        final ICommand keyCommandW = commandFactory.getCommand("WP", 0.0);
        final ICommand keyCommandNE = commandFactory.getCommand("NEP", 0.0);
        final ICommand keyCommandNW = commandFactory.getCommand("NWP", 0.0);
        final ICommand keyCommandSE = commandFactory.getCommand("SEP", 0.0);
        final ICommand keyCommandSW = commandFactory.getCommand("SWP", 0.0);

        assertThat(keyCommandN).isEqualTo(nExpected);
        assertThat(keyCommandS).isEqualTo(sExpected);
        assertThat(keyCommandE).isEqualTo(eExpected);
        assertThat(keyCommandW).isEqualTo(wExpected);
        assertThat(keyCommandNE).isEqualTo(neExpected);
        assertThat(keyCommandNW).isEqualTo(nwExpected);
        assertThat(keyCommandSE).isEqualTo(seExpected);
        assertThat(keyCommandSW).isEqualTo(swExpected);
    }

    @Test
    public void load_mouse_click() throws Exception {

        // given
        final InputUtils inputUtils = mock(InputUtils.class);

        final Key key = new Key("LEFT_MOUSE");
        final Button button = new Button("A", key);
        final Configuration configuration = new Configuration(button);

        // expected
        final MouseCommand expected = new MouseCommand(MouseButton.LEFT,
                inputUtils);

        // when
        final CommandFactory commandFactory = new CommandFactory(inputUtils,
                configuration);

        // then
        final ICommand command = commandFactory.getCommand("A", 0.0);
        assertThat(command).isEqualTo(expected);
    }
}
