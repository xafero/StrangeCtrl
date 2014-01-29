package pl.grzeslowski.strangectrl.cmd;

import static com.google.common.base.Objects.equal;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import pl.grzeslowski.strangectrl.config.Button;
import pl.grzeslowski.strangectrl.config.Configuration;
import pl.grzeslowski.strangectrl.config.Key;

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
}
