package pl.grzeslowski.integration_tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.GraphicsDevice;

import org.junit.Test;

import pl.grzeslowski.strangectrl.cmd.CommandFactory;
import pl.grzeslowski.strangectrl.config.Configuration;
import pl.grzeslowski.strangectrl.config.Key;
import pl.grzeslowski.strangectrl.config.XStreamConfigLoader;

import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.InputUtils;

public class MappersTests {

	@Test
	public void dont_use_mapper() throws Exception {

		// given
		final String xml = "<configuration><button value=\"X\"><key key=\"Z\" /></button><button value=\"0\"><key key=\"D\" /></button></configuration>";

		final XStreamConfigLoader configLoader = new XStreamConfigLoader();
		final Configuration configuration = configLoader.loadXml(xml);

		final InputUtils inputUtils = mock(InputUtils.class);

		final CommandFactory factory = new CommandFactory(
				inputUtils,
				configuration);

		// when
		final ICommand command = factory.getCommand("0", 1.0);
		command.execute(mock(GraphicsDevice.class), 1.0);

		// then
		verify(inputUtils).pressKey(new Key("D"));
	}

	@Test
	public void use_mapper() throws Exception {

		// given
		final String xml = "<configuration><button value=\"X\"><key key=\"Z\" /></button><button value=\"0\"><key key=\"D\" /></button></configuration>";

		final XStreamConfigLoader configLoader = new XStreamConfigLoader();
		final Configuration configuration = configLoader.loadXml(xml);

		final InputUtils inputUtils = mock(InputUtils.class);

		final CommandFactory factory = new CommandFactory(inputUtils,
				configuration);

		// when
		final ICommand command = factory.getCommand("2", 1.0);
		command.execute(mock(GraphicsDevice.class), 1.0);

		// then
		verify(inputUtils).pressKey(new Key("Z"));
	}
}
