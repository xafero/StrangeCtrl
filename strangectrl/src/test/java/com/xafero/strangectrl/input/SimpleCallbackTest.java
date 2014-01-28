package com.xafero.strangectrl.input;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.GraphicsDevice;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Event;

import org.junit.Ignore;
import org.junit.Test;

import pl.grzeslowski.strangectrl.cmd.CommandFactory;

import com.xafero.strangectrl.cmd.ICommand;

public class SimpleCallbackTest {

	@Test
	public void execute_command_for_controller() throws Exception {

		// given
		final ICommand command = mock(ICommand.class);
		final CommandFactory commandFactory = mock(CommandFactory.class);
		when(commandFactory.getCommand("x")).thenReturn(command);

		final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
		final SimpleCallback callback = new SimpleCallback(commandFactory,
				graphicsDevice);

		final Component component = mock(Component.class);
		when(component.getIdentifier()).thenReturn(Identifier.Axis.X);

		final Event event = new Event();
		final float value = 0.333f;
		event.set(component, value, 0);

		// when
		callback.onNewEvent(event);

		// then
		verify(command).execute(graphicsDevice, value);
	}

	@Test
	public void execute_period_command_for_controller() throws Exception {

		// given
		final ICommand command = mock(ICommand.class);
		when(command.isPeriodCommand()).thenReturn(true);

		final CommandFactory commandFactory = mock(CommandFactory.class);
		when(commandFactory.getCommand("x")).thenReturn(command);

		final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
		final SimpleCallback callback = new SimpleCallback(commandFactory,
				graphicsDevice);

		final Component component = mock(Component.class);
		when(component.getIdentifier()).thenReturn(Identifier.Axis.X);

		final Event event = new Event();
		final float value = 0.333f;
		event.set(component, value, 0);

		// when
		callback.onNewEvent(event);

		// then
		verify(command).execute(graphicsDevice, value);
	}

	@Test
	public void execute_pov_command_for_controller() throws Exception {

		// given
		final ICommand command = mock(ICommand.class);
		final CommandFactory commandFactory = mock(CommandFactory.class);
		when(commandFactory.getCommand("NP")).thenReturn(command);

		final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
		final SimpleCallback callback = new SimpleCallback(commandFactory,
				graphicsDevice);

		final Identifier identifier = mock(Identifier.class);
		when(identifier.getName()).thenReturn("pov");

		final Component component = mock(Component.class);
		when(component.getIdentifier()).thenReturn(identifier);

		final Event event = new Event();
		final float value = 0.25f;
		event.set(component, value, 0);

		// when
		callback.onNewEvent(event);

		// then
		verify(command).execute(graphicsDevice, 1.0);
	}

	@Ignore
	// see bug #11
	@Test
	public void execute_pov_period_command_for_controller_then_release()
			throws Exception {

		// given
		final ICommand command = mock(ICommand.class);
		when(command.isPeriodCommand()).thenReturn(true);

		final CommandFactory commandFactory = mock(CommandFactory.class);
		when(commandFactory.getCommand("NP")).thenReturn(command);

		final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
		final SimpleCallback callback = new SimpleCallback(commandFactory,
				graphicsDevice);

		final Identifier identifier = mock(Identifier.class);
		when(identifier.getName()).thenReturn("pov");

		final Component component = mock(Component.class);
		when(component.getIdentifier()).thenReturn(identifier);

		final Event eventPush = new Event();
		eventPush.set(component, 0.25f, 0);

		final Event eventRelease = new Event();
		eventRelease.set(component, 0.0f, 0);

		// when
		callback.onNewEvent(eventPush);
		callback.doPeriodCommands();
		callback.onNewEvent(eventRelease);
		callback.doPeriodCommands();

		// then
		verify(command, times(2)).execute(graphicsDevice, 1.0);
		verify(command, times(2)).execute(graphicsDevice, 0.0);
	}

	@Test
	public void execute_period_command_for_controller_second_time()
			throws Exception {

		// given
		final ICommand command = mock(ICommand.class);
		when(command.isPeriodCommand()).thenReturn(true);

		final CommandFactory commandFactory = mock(CommandFactory.class);
		when(commandFactory.getCommand("x")).thenReturn(command);

		final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
		final SimpleCallback callback = new SimpleCallback(commandFactory,
				graphicsDevice);

		final Component component = mock(Component.class);
		when(component.getIdentifier()).thenReturn(Identifier.Axis.X);

		final Event event = new Event();
		final float value = 0.333f;
		event.set(component, value, 0);

		// when
		callback.onNewEvent(event);
		callback.doPeriodCommands();

		// then
		verify(command, times(2)).execute(graphicsDevice, value);
	}

	@Test
	public void execute_period_command_for_controller_second_time_then_goes_new_event()
			throws Exception {

		// given
		final ICommand command = mock(ICommand.class);
		when(command.isPeriodCommand()).thenReturn(true);

		final CommandFactory commandFactory = mock(CommandFactory.class);
		when(commandFactory.getCommand("x")).thenReturn(command);

		final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
		final SimpleCallback callback = new SimpleCallback(commandFactory,
				graphicsDevice);

		final Component component = mock(Component.class);
		when(component.getIdentifier()).thenReturn(Identifier.Axis.X);

		final Event event = new Event();
		final float value = 0.333f;
		event.set(component, value, 0);

		final Event secondEvent = new Event();
		final float secondValue = 0.777f;
		secondEvent.set(component, secondValue, 0);

		// when
		callback.onNewEvent(event);
		callback.doPeriodCommands();
		callback.onNewEvent(secondEvent);
		callback.doPeriodCommands();
		callback.doPeriodCommands();

		// then
		verify(command, times(2)).execute(graphicsDevice, value);
		verify(command, times(3)).execute(graphicsDevice, secondValue);
	}

	@Test
	public void execute_period_command_for_controller_second_time_value_0()
			throws Exception {

		// given
		final ICommand command = mock(ICommand.class);
		when(command.isPeriodCommand()).thenReturn(true);

		final CommandFactory commandFactory = mock(CommandFactory.class);
		when(commandFactory.getCommand("x")).thenReturn(command);

		final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
		final SimpleCallback callback = new SimpleCallback(commandFactory,
				graphicsDevice);

		final Component component = mock(Component.class);
		when(component.getIdentifier()).thenReturn(Identifier.Axis.X);

		final Event event = new Event();
		final float value = 0.333f;
		event.set(component, value, 0);

		final Event eventZero = new Event();
		eventZero.set(component, 0, 0);

		// when
		callback.onNewEvent(event);
		callback.doPeriodCommands();
		callback.onNewEvent(eventZero);

		// then
		verify(command, times(2)).execute(graphicsDevice, value);
	}

	@Test
	public void add_command_to_execute() throws Exception {

		// given
		final ICommand command = mock(ICommand.class);
		final CommandFactory commandFactory = mock(CommandFactory.class);
		when(commandFactory.getCommand("x")).thenReturn(command);

		final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
		final SimpleCallback callback = new SimpleCallback(commandFactory,
				graphicsDevice);

		final Component component = mock(Component.class);
		when(component.getIdentifier()).thenReturn(Identifier.Axis.X);

		final Event event = new Event();
		final float value = 0.333f;
		event.set(component, value, 0);

		// when
		callback.onNewEvent(event);

		// then
		verify(command).execute(graphicsDevice, value);
	}

	@Test
	public void add_command_to_execute_two_times() throws Exception {

		// given
		final ICommand command = mock(ICommand.class);
		final CommandFactory commandFactory = mock(CommandFactory.class);
		when(commandFactory.getCommand("x")).thenReturn(command);

		final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
		final SimpleCallback callback = new SimpleCallback(commandFactory,
				graphicsDevice);

		final Component component = mock(Component.class);
		when(component.getIdentifier()).thenReturn(Identifier.Axis.X);

		final Event event = new Event();
		final float value = 0.333f;
		event.set(component, value, 0);

		// when
		callback.onNewEvent(event);
		callback.onNewEvent(event);

		// then
		verify(command, times(2)).execute(graphicsDevice, value);
	}

	@Test
	public void add_command_to_execute_then_remove() throws Exception {

		// given
		final ICommand command = mock(ICommand.class);
		final CommandFactory commandFactory = mock(CommandFactory.class);
		when(commandFactory.getCommand("x")).thenReturn(command);

		final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
		final SimpleCallback callback = new SimpleCallback(commandFactory,
				graphicsDevice);

		final Component component = mock(Component.class);
		when(component.getIdentifier()).thenReturn(Identifier.Axis.X);

		final Event event = new Event();
		final float value = 0.333f;
		event.set(component, value, 0);

		final Event eventZero = new Event();
		eventZero.set(component, 0, 0);

		// when
		callback.onNewEvent(event);
		callback.onNewEvent(eventZero);

		// then
		verify(command).execute(graphicsDevice, value);
	}

	@Test
	public void when_controlled_removed_flush_all_period_commands()
			throws Exception {

		// given
		final ICommand command = mock(ICommand.class);
		when(command.isPeriodCommand()).thenReturn(true);

		final CommandFactory commandFactory = mock(CommandFactory.class);
		when(commandFactory.getCommand("x")).thenReturn(command);

		final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
		final SimpleCallback callback = new SimpleCallback(commandFactory,
				graphicsDevice);

		final Component component = mock(Component.class);
		when(component.getIdentifier()).thenReturn(Identifier.Axis.X);

		final Event event = new Event();
		final float value = 0.333f;
		event.set(component, value, 0);

		// when
		callback.onNewEvent(event);
		callback.doPeriodCommands();
		callback.controllerRemoved();

		// then
		verify(command, times(2)).execute(graphicsDevice, value);
		verify(command).execute(graphicsDevice, 0.0);
	}

	@Test
	public void when_controlled_removed_flush_all_normal_commands()
			throws Exception {

		// given
		final ICommand command = mock(ICommand.class);
		when(command.isPeriodCommand()).thenReturn(false);

		final CommandFactory commandFactory = mock(CommandFactory.class);
		when(commandFactory.getCommand("x")).thenReturn(command);

		final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
		final SimpleCallback callback = new SimpleCallback(commandFactory,
				graphicsDevice);

		final Component component = mock(Component.class);
		when(component.getIdentifier()).thenReturn(Identifier.Axis.X);

		final Event event = new Event();
		final float value = 0.333f;
		event.set(component, value, 0);

		// when
		callback.onNewEvent(event);
		callback.doPeriodCommands();
		callback.controllerRemoved();

		// then
		verify(command).execute(graphicsDevice, value);
		verify(command).execute(graphicsDevice, 0.0);
	}

	@Test
	public void reuse_callback_after_adding_period_command() throws Exception {

		// given - first use
		final ICommand command = mock(ICommand.class);
		when(command.isPeriodCommand()).thenReturn(true);

		final Component component = mock(Component.class);
		when(component.getIdentifier()).thenReturn(Identifier.Axis.X);

		final Event event = new Event();
		final float value = 0.333f;
		event.set(component, value, 0);

		// given - second use
		final ICommand commandSecondUse = mock(ICommand.class);
		when(commandSecondUse.isPeriodCommand()).thenReturn(true);

		final Component componentSecondUse = mock(Component.class);
		when(componentSecondUse.getIdentifier()).thenReturn(Identifier.Axis.Y);

		final Event eventSecondUse = new Event();
		final float valueSecondUse = 0.667f;
		eventSecondUse.set(componentSecondUse, valueSecondUse, 0);

		// given
		final CommandFactory commandFactory = mock(CommandFactory.class);
		when(commandFactory.getCommand("x")).thenReturn(command);
		when(commandFactory.getCommand("y")).thenReturn(commandSecondUse);

		final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
		final SimpleCallback callback = new SimpleCallback(commandFactory,
				graphicsDevice);

		// when - first use
		callback.onNewEvent(event);
		callback.doPeriodCommands();
		callback.controllerRemoved();

		// when - second use
		callback.onNewEvent(eventSecondUse);
		callback.doPeriodCommands();
		callback.controllerRemoved();

		// then - first use
		verify(command, times(2)).execute(graphicsDevice, value);
		verify(command).execute(graphicsDevice, 0.0);

		// then - second use
		verify(commandSecondUse, times(2)).execute(graphicsDevice,
				valueSecondUse);
		verify(commandSecondUse).execute(graphicsDevice, 0.0);
	}

	@Test
	public void reuse_callback_after_adding_command() throws Exception {

		// given - first use
		final ICommand command = mock(ICommand.class);
		when(command.isPeriodCommand()).thenReturn(false);

		final Component component = mock(Component.class);
		when(component.getIdentifier()).thenReturn(Identifier.Axis.X);

		final Event event = new Event();
		final float value = 0.333f;
		event.set(component, value, 0);

		// given - second use
		final ICommand commandSecondUse = mock(ICommand.class);
		when(commandSecondUse.isPeriodCommand()).thenReturn(false);

		final Component componentSecondUse = mock(Component.class);
		when(componentSecondUse.getIdentifier()).thenReturn(Identifier.Axis.Y);

		final Event eventSecondUse = new Event();
		final float valueSecondUse = 0.667f;
		eventSecondUse.set(componentSecondUse, valueSecondUse, 0);

		// given
		final CommandFactory commandFactory = mock(CommandFactory.class);
		when(commandFactory.getCommand("x")).thenReturn(command);
		when(commandFactory.getCommand("y")).thenReturn(commandSecondUse);

		final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
		final SimpleCallback callback = new SimpleCallback(commandFactory,
				graphicsDevice);

		// when - first use
		callback.onNewEvent(event);
		callback.doPeriodCommands();
		callback.controllerRemoved();

		// when - second use
		callback.onNewEvent(eventSecondUse);
		callback.doPeriodCommands();
		callback.controllerRemoved();

		// then - first use
		verify(command).execute(graphicsDevice, value);
		verify(command).execute(graphicsDevice, 0.0);

		// then - second use
		verify(commandSecondUse).execute(graphicsDevice, valueSecondUse);
		verify(commandSecondUse).execute(graphicsDevice, 0.0);
	}

}
