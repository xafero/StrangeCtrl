package pl.grzeslowski.strangectrl.cmd;

import static com.google.common.base.Objects.equal;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pl.grzeslowski.strangectrl.cmd.mappers.CommandNameMapper;
import pl.grzeslowski.strangectrl.config.Button;
import pl.grzeslowski.strangectrl.config.Configuration;
import pl.grzeslowski.strangectrl.config.Key;
import pl.grzeslowski.strangectrl.config.Pov;

import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.InputUtils;

public class CommandFactoryUseRawIdentifiersTest {

    @Test
    public void try_to_use_0_identifier() throws Exception {

        // given
        final Key key = new Key("D");
        final Button[] buttons = { new Button("0", key) };
        final Configuration configuration = new Configuration(buttons);
        final CommandFactory factory = new CommandFactory(
                mock(InputUtils.class), configuration);

        // when
        final ICommand command0 = factory.getCommand("0", 0.0);
        final ICommand commandNull = factory.getCommand("1", 0.0);

        // then
        assertThat(command0).isNotNull();
        assertThat(commandNull).isNull();
    }

    @Test
    public void try_to_use_B_identifier() throws Exception {

        // given
        final Key key = new Key("D");
        final Button[] buttons = { new Button("B", key) };
        final Configuration configuration = new Configuration(buttons);
        final CommandFactory factory = new CommandFactory(
                mock(InputUtils.class), configuration);

        // when
        final ICommand command0 = factory.getCommand("1", 0.0);
        final ICommand commandNull = factory.getCommand("100", 0.0);

        // then
        assertThat(command0).isNotNull();
        assertThat(commandNull).isNull();
    }

    @Test
    public void try_to_use_with_new_mapper() throws Exception {

        // given
        final String ident = "ident";
        final CommandNameMapper mapper = new CommandNameMapper() {

            @Override
            public String map(final String identifier, final double value) {
                if (equal(identifier, ident)) {
                    return "B";
                } else {
                    return null;
                }
            }

            @Override
            public boolean canMap(final String identifier, final double value) {
                return equal(identifier, ident);
            }
        };
        final Button[] buttons = { new Button("B", new Key("D")) };
        final Configuration configuration = new Configuration(buttons);
        final CommandFactory factory = new CommandFactory(
                mock(InputUtils.class), configuration, mapper);

        // when
        final ICommand command0 = factory.getCommand(ident, 0.0);
        final ICommand commandNull = factory.getCommand("100", 0.0);

        // then
        assertThat(command0).isNotNull();
        assertThat(commandNull).isNull();
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
        final ICommand xboxA = factory.getCommand("0", 0);
        final ICommand xboxB = factory.getCommand("1", 0);
        final ICommand xboxX = factory.getCommand("2", 0);
        final ICommand xboxY = factory.getCommand("3", 0);
        final ICommand xboxStart = factory.getCommand("7", 0);
        final ICommand xboxBack = factory.getCommand("6", 0);
        final ICommand xboxRb = factory.getCommand("5", 0);
        final ICommand xboxLb = factory.getCommand("4", 0);
        final ICommand xboxRs = factory.getCommand("9", 0);
        final ICommand xboxLs = factory.getCommand("8", 0);

        final ICommand povN = factory.getCommand("pov", 0.25);
        final ICommand povS = factory.getCommand("pov", 0.75);
        final ICommand povE = factory.getCommand("pov", 0.5);
        final ICommand povW = factory.getCommand("pov", 1);
        final ICommand povNe = factory.getCommand("pov", 0.375);
        final ICommand povNw = factory.getCommand("pov", 0.125);
        final ICommand povSe = factory.getCommand("pov", 0.625);
        final ICommand povSw = factory.getCommand("pov", 0.875);
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
