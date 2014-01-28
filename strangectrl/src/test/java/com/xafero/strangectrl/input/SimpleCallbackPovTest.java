package com.xafero.strangectrl.input;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.GraphicsDevice;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

import pl.grzeslowski.strangectrl.cmd.CommandFactory;

import com.google.common.collect.Sets;
import com.xafero.strangectrl.cmd.ICommand;

@RunWith(value = Parameterized.class)
public class SimpleCallbackPovTest {

	private final float value;
	private final String commandName;

	public SimpleCallbackPovTest(final float value, final String commandName) {
		this.value = value;
		this.commandName = commandName;
	}

	@Parameters
	public static Collection<Object[]> data() {
		final Object[][] data = new Object[][] { { 0.125f, "NWP" },
				{ 0.25f, "NP" }, { 0.375f, "NEP" }, { 0.5f, "EP" },
				{ 0.625f, "SEP" }, { 0.75f, "SP" }, { 0.875f, "SWP" },
				{ 1, "WP" } };
		return Arrays.asList(data);
	}

	@Test
	public void execute_SWP_pov_command_for_controller_then_release()
			throws Exception {

		// given
		final ICommand cNwp = mock(ICommand.class);
		final ICommand cNp = mock(ICommand.class);
		final ICommand cNep = mock(ICommand.class);
		final ICommand cEp = mock(ICommand.class);
		final ICommand cSep = mock(ICommand.class);
		final ICommand cSp = mock(ICommand.class);
		final ICommand cSwp = mock(ICommand.class);
		final ICommand cWp = mock(ICommand.class);

		final CommandFactory commandFactory = mock(CommandFactory.class);
		when(commandFactory.getCommand("NWP")).thenReturn(cNwp);
		when(commandFactory.getCommand("NP")).thenReturn(cNp);
		when(commandFactory.getCommand("NEP")).thenReturn(cNep);
		when(commandFactory.getCommand("EP")).thenReturn(cEp);
		when(commandFactory.getCommand("SEP")).thenReturn(cSep);
		when(commandFactory.getCommand("SP")).thenReturn(cSp);
		when(commandFactory.getCommand("SWP")).thenReturn(cSwp);
		when(commandFactory.getCommand("WP")).thenReturn(cWp);

		final Set<ICommand> commands = Sets.newHashSet(cNwp, cNp, cNep, cEp,
				cSep, cSp, cSwp, cWp);

		final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
		final SimpleCallback callback = new SimpleCallback(commandFactory,
				graphicsDevice);

		final Identifier identifier = mock(Identifier.class);
		when(identifier.getName()).thenReturn("pov");

		final Component component = mock(Component.class);
		when(component.getIdentifier()).thenReturn(identifier);

		final Event eventPush = new Event();
		eventPush.set(component, value, 0);

		final Event eventRelease = new Event();
		eventRelease.set(component, 0.0f, 0);

		// when
		callback.onNewEvent(eventPush);
		callback.onNewEvent(eventRelease);

		// then
		final ICommand commandToUse = commandFactory.getCommand(commandName);
		verify(commandToUse).execute(graphicsDevice, 1.0);
		verify(commandToUse).execute(graphicsDevice, 0.0);

		commands.remove(commandToUse);
		for (final ICommand iCommand : commands) {
			Mockito.verifyZeroInteractions(iCommand);
		}
	}
}
