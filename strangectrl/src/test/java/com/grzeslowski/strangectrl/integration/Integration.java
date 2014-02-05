package com.grzeslowski.strangectrl.integration;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;

import org.junit.Test;

import pl.grzeslowski.strangectrl.cmd.ComboKeyCommand;
import pl.grzeslowski.strangectrl.cmd.CommandFactory;
import pl.grzeslowski.strangectrl.cmd.MouseCommand;
import pl.grzeslowski.strangectrl.cmd.MouseMoveXCommand;
import pl.grzeslowski.strangectrl.cmd.MouseMoveYCommand;
import pl.grzeslowski.strangectrl.cmd.MouseWheelCommand;
import pl.grzeslowski.strangectrl.cmd.PovKeyCommand;
import pl.grzeslowski.strangectrl.cmd.SequentialKeyCommand;
import pl.grzeslowski.strangectrl.config.ConfigReader;
import pl.grzeslowski.strangectrl.config.Configuration;
import pl.grzeslowski.strangectrl.config.Key;
import pl.grzeslowski.strangectrl.config.Setup;

import com.xafero.strangectrl.awt.DesktopUtils;
import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.InputUtils;
import com.xafero.strangectrl.input.InputUtils.MouseButton;

public class Integration {

    @Test
    public void load_config_from_string_and_get_all_commands() throws Exception {

        final InputUtils inputUtils=mock(InputUtils.class);
        final DesktopUtils desktopUtils=mock(DesktopUtils.class);
        final Setup setup = Setup.getDefaultSetup();

        // @formatter:off
        // expected
        // xbox buttons
        final ICommand aE = new SequentialKeyCommand(new Key("ENTER"), inputUtils);
        final ICommand bE = new SequentialKeyCommand(new Key("BACK_SPACE"), inputUtils);
        final ICommand xE = new SequentialKeyCommand(new Key("ESCAPE"), inputUtils);
        final ICommand yE = new ComboKeyCommand(Arrays.asList(new Key("ALT"), new Key("TAB")), inputUtils);
        final ICommand backE =  new ComboKeyCommand(Arrays.asList(new Key("CONTROL"), new Key("SHIFT"), new Key("T")), inputUtils);
        final ICommand startE =  new ComboKeyCommand(Arrays.asList(new Key("CONTROL"), new Key("T")), inputUtils);
        final ICommand lsE =  new ComboKeyCommand(Arrays.asList(new Key("CONTROL"), new Key("W")), inputUtils);
        final ICommand rsE = new MouseCommand(MouseButton.CENTER, inputUtils);
        final ICommand lbE = new MouseCommand(MouseButton.RIGHT, inputUtils);
        final ICommand rbE = new MouseCommand(MouseButton.LEFT, inputUtils);

        // pov
        final ICommand notPovE = null;
        final ICommand nwE = new PovKeyCommand(new SequentialKeyCommand(Arrays.asList(new Key("UP"), new Key("LEFT")), inputUtils));
        final ICommand nE = new PovKeyCommand(new SequentialKeyCommand(new Key("UP"), inputUtils));
        final ICommand neE = new PovKeyCommand(new SequentialKeyCommand(Arrays.asList(new Key("UP"), new Key("RIGHT")), inputUtils));
        final ICommand eE = new PovKeyCommand(new SequentialKeyCommand(new Key("RIGHT"), inputUtils));
        final ICommand seE = new PovKeyCommand(new SequentialKeyCommand(Arrays.asList(new Key("DOWN"), new Key("RIGHT")), inputUtils));
        final ICommand sE = new PovKeyCommand(new SequentialKeyCommand(new Key("DOWN"), inputUtils));
        final ICommand swE = new PovKeyCommand(new SequentialKeyCommand(Arrays.asList(new Key("DOWN"), new Key("LEFT")), inputUtils));
        final ICommand wE = new PovKeyCommand(new SequentialKeyCommand(new Key("LEFT"), inputUtils));

        // analogs
        final ICommand mouseXE = new MouseMoveXCommand(inputUtils, setup.getMaxMouseMove(), CommandFactory.DELTA_FOR_MOUSE_MOVE, desktopUtils);
        final ICommand mouseYE = new MouseMoveYCommand(inputUtils, setup.getMaxMouseMove(), CommandFactory.DELTA_FOR_MOUSE_MOVE, desktopUtils);
        final ICommand scrollE = new MouseWheelCommand(inputUtils, setup.getScrollLines(), CommandFactory.DELTA_FOR_SCROLL);

        // @formatter:on
        final ConfigReader configReader = new ConfigReader();
        final Configuration configuration = configReader.loadConfiguration();

        final CommandFactory factory = new CommandFactory(
                mock(InputUtils.class), configuration);

        // @formatter:off
        // xbox buttons
        final ICommand a = factory.getCommand("A", 1.0);
        final ICommand b = factory.getCommand("B", 1.0);
        final ICommand x = factory.getCommand("X", 1.0);
        final ICommand y = factory.getCommand("Y", 1.0);
        final ICommand back = factory.getCommand("BACK", 1.0);
        final ICommand start = factory.getCommand("START", 1.0);
        final ICommand ls = factory.getCommand("LS", 1.0);
        final ICommand rs = factory.getCommand("RS", 1.0);
        final ICommand lb = factory.getCommand("LB", 1.0);
        final ICommand rb = factory.getCommand("RB", 1.0);

        // pov
        final ICommand notPov = factory.getCommand("pov", 0.0);
        final ICommand nw = factory.getCommand("pov", 0.125);
        final ICommand n = factory.getCommand("pov", 0.25);
        final ICommand ne = factory.getCommand("pov", 0.375);
        final ICommand e = factory.getCommand("pov", 0.5);
        final ICommand se = factory.getCommand("pov", 0.625);
        final ICommand s = factory.getCommand("pov", 0.75);
        final ICommand sw = factory.getCommand("pov", 0.875);
        final ICommand w = factory.getCommand("pov", 1.0);

        // analogs
        final ICommand mouseX = factory.getCommand("x", 1.0);
        final ICommand mouseY = factory.getCommand("y", 1.0);
        final ICommand scroll = factory.getCommand("ry", 1.0);
        // @formatter:on

        // then
        // xbox
        assertThat(a).isEqualTo(aE);
        assertThat(b).isEqualTo(bE);
        assertThat(x).isEqualTo(xE);
        assertThat(y).isEqualTo(yE);
        assertThat(back).isEqualTo(backE);
        assertThat(start).isEqualTo(startE);
        assertThat(ls).isEqualTo(lsE);
        assertThat(rs).isEqualTo(rsE);
        assertThat(lb).isEqualTo(lbE);
        assertThat(rb).isEqualTo(rbE);

        // pov
        assertThat(notPov).isEqualTo(notPovE);
        assertThat(nw).isEqualTo(nwE);
        assertThat(n).isEqualTo(nE);
        assertThat(ne).isEqualTo(neE);
        assertThat(e).isEqualTo(eE);
        assertThat(se).isEqualTo(seE);
        assertThat(s).isEqualTo(sE);
        assertThat(sw).isEqualTo(swE);
        assertThat(w).isEqualTo(wE);

        // analogs
        assertThat(mouseX).isEqualTo(mouseXE);
        assertThat(mouseY).isEqualTo(mouseYE);
        assertThat(scroll).isEqualTo(scrollE);
    }
}
