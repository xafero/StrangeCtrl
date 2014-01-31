package pl.grzeslowski.strangectrl.cmd;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
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
        final KeyCommand expected = new SequentialKeyCommand(key, inputUtils);

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
        final KeyCommand expected1 = new SequentialKeyCommand(key1, inputUtils);
        final KeyCommand expected2 = new SequentialKeyCommand(
                Lists.newArrayList(key1, key2), inputUtils);

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

    @Test
    public void check_default_mappers() throws Exception {

        // given
        final List<Button> xboxButtons = new ArrayList<>(10);
        xboxButtons.add(new Button("A", new Key("Q")));
        xboxButtons.add(new Button("B", new Key("W")));
        xboxButtons.add(new Button("X", new Key("E")));
        xboxButtons.add(new Button("Y", new Key("R")));
        xboxButtons.add(new Button("START", new Key("T")));
        xboxButtons.add(new Button("BACK", new Key("Y")));
        xboxButtons.add(new Button("RB", new Key("U")));
        xboxButtons.add(new Button("LB", new Key("I")));
        xboxButtons.add(new Button("RS", new Key("O")));
        xboxButtons.add(new Button("LS", new Key("P")));

        final List<Button> povButtons = new ArrayList<>(8);
        povButtons.add(new Button("NP", new Key("A")));
        povButtons.add(new Button("SP", new Key("S")));
        povButtons.add(new Button("EP", new Key("D")));
        povButtons.add(new Button("WP", new Key("F")));
        povButtons.add(new Button("NEP", new Key("G")));
        povButtons.add(new Button("NWP", new Key("H")));
        povButtons.add(new Button("SEP", new Key("J")));
        povButtons.add(new Button("SWP", new Key("K")));

        final Pov pov = new Pov(povButtons);

        final Configuration configuration = new Configuration(xboxButtons, pov);
        final InputUtils inputUtils = mock(InputUtils.class);
        final CommandFactory factory = new CommandFactory(inputUtils,
                configuration);

        // expected
        // @formatter:off
        final ICommand eXboxA = new SequentialKeyCommand(new Key("Q"), inputUtils);
        final ICommand eXboxB = new SequentialKeyCommand(new Key("W"), inputUtils);
        final ICommand eXboxX = new SequentialKeyCommand(new Key("E"), inputUtils);
        final ICommand eXboxY = new SequentialKeyCommand(new Key("R"), inputUtils);
        final ICommand eXboxStart = new SequentialKeyCommand(new Key("T"), inputUtils);
        final ICommand eXboxBack = new SequentialKeyCommand(new Key("Y"), inputUtils);
        final ICommand eXboxRb = new SequentialKeyCommand(new Key("U"), inputUtils);
        final ICommand eXboxLb = new SequentialKeyCommand(new Key("I"), inputUtils);
        final ICommand eXboxRs = new SequentialKeyCommand(new Key("O"), inputUtils);
        final ICommand eXboxLs = new SequentialKeyCommand(new Key("P"), inputUtils);

        final ICommand ePovN = new PovKeyCommand(new SequentialKeyCommand(new Key("A"), inputUtils));
        final ICommand ePovS = new PovKeyCommand(new SequentialKeyCommand(new Key("S"), inputUtils));
        final ICommand ePovE = new PovKeyCommand(new SequentialKeyCommand(new Key("D"), inputUtils));
        final ICommand ePovW = new PovKeyCommand(new SequentialKeyCommand(new Key("F"), inputUtils));
        final ICommand ePovNe = new PovKeyCommand(new SequentialKeyCommand(new Key("G"), inputUtils));
        final ICommand ePovNw = new PovKeyCommand(new SequentialKeyCommand(new Key("H"), inputUtils));
        final ICommand ePovSe = new PovKeyCommand(new SequentialKeyCommand(new Key("J"), inputUtils));
        final ICommand ePovSw = new PovKeyCommand(new SequentialKeyCommand(new Key("K"), inputUtils));

        // when
        final ICommand xboxA = factory.getCommand("A", 0);
        final ICommand xboxB = factory.getCommand("B", 0);
        final ICommand xboxX = factory.getCommand("X", 0);
        final ICommand xboxY = factory.getCommand("Y", 0);
        final ICommand xboxStart = factory.getCommand("START", 0);
        final ICommand xboxBack = factory.getCommand("BACK", 0);
        final ICommand xboxRb = factory.getCommand("RB", 0);
        final ICommand xboxLb = factory.getCommand("LB", 0);
        final ICommand xboxRs = factory.getCommand("RS", 0);
        final ICommand xboxLs = factory.getCommand("LS", 0);

        final ICommand povN = factory.getCommand("NP", 0);
        final ICommand povS = factory.getCommand("SP", 0);
        final ICommand povE = factory.getCommand("EP", 0);
        final ICommand povW = factory.getCommand("WP", 0);
        final ICommand povNe = factory.getCommand("NEP", 0);
        final ICommand povNw = factory.getCommand("NWP", 0);
        final ICommand povSe = factory.getCommand("SEP", 0);
        final ICommand povSw = factory.getCommand("SWP", 0);
        // @formatter:on

        // then
        assertThat(xboxA).isEqualTo(eXboxA);
        assertThat(xboxB).isEqualTo(eXboxB);
        assertThat(xboxX).isEqualTo(eXboxX);
        assertThat(xboxY).isEqualTo(eXboxY);
        assertThat(xboxStart).isEqualTo(eXboxStart);
        assertThat(xboxBack).isEqualTo(eXboxBack);
        assertThat(xboxRb).isEqualTo(eXboxRb);
        assertThat(xboxLb).isEqualTo(eXboxLb);
        assertThat(xboxRs).isEqualTo(eXboxRs);
        assertThat(xboxLs).isEqualTo(eXboxLs);

        assertThat(povN).isEqualTo(ePovN);
        assertThat(povS).isEqualTo(ePovS);
        assertThat(povE).isEqualTo(ePovE);
        assertThat(povW).isEqualTo(ePovW);
        assertThat(povNe).isEqualTo(ePovNe);
        assertThat(povNw).isEqualTo(ePovNw);
        assertThat(povSe).isEqualTo(ePovSe);
        assertThat(povSw).isEqualTo(ePovSw);
    }
}
