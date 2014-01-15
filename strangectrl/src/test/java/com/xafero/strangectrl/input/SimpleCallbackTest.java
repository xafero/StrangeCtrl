package com.xafero.strangectrl.input;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.GraphicsDevice;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.Event;

import org.junit.Test;

import pl.grzeslowski.strangectrl.cmd.CommandFactory;

import com.xafero.strangectrl.cmd.ICommand;

public class SimpleCallbackTest {

    @Test
    public void remove() throws Exception {

        // given
        final ICommand command = mock(ICommand.class);
        when(command.isPeriodCommand()).thenReturn(true);

        final CommandFactory commandFactory = mock(CommandFactory.class);
        when(commandFactory.getCommand("x")).thenReturn(command);
        when(commandFactory.getCommand("y")).thenReturn(command);

        final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
        final SimpleCallback callback = new SimpleCallback(commandFactory,
                graphicsDevice);

        final Controller controller = mock(Controller.class);

        final Component component1 = mock(Component.class);
        when(component1.getIdentifier()).thenReturn(Identifier.Axis.X);

        final Event event1 = new Event();
        event1.set(component1, 0, 0);

        // when
        callback.onNewEvent(null, controller, event1);
        callback.removeController(controller);

        // then
        assertThat(callback.containsCommandsFor(controller)).isFalse();
    }

    @Test
    public void remove_two_commands() throws Exception {

        // given
        final ICommand command = mock(ICommand.class);
        when(command.isPeriodCommand()).thenReturn(true);

        final CommandFactory commandFactory = mock(CommandFactory.class);
        when(commandFactory.getCommand("x")).thenReturn(command);
        when(commandFactory.getCommand("y")).thenReturn(command);

        final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
        final SimpleCallback callback = new SimpleCallback(commandFactory,
                graphicsDevice);

        final Controller controller = mock(Controller.class);

        final Component component1 = mock(Component.class);
        when(component1.getIdentifier()).thenReturn(Identifier.Axis.X);

        final Event event1 = new Event();
        event1.set(component1, 0, 0);

        final Component component2 = mock(Component.class);
        when(component2.getIdentifier()).thenReturn(Identifier.Axis.Y);

        final Event event2 = new Event();
        event2.set(component2, 0, 0);

        // when
        callback.onNewEvent(null, controller, event1);
        callback.onNewEvent(null, controller, event2);
        callback.removeController(controller);

        // then
        assertThat(callback.containsCommandsFor(controller)).isFalse();
    }

    @Test
    public void add_remove_add() throws Exception {

        // given
        final ICommand command = mock(ICommand.class);
        when(command.isPeriodCommand()).thenReturn(true);

        final CommandFactory commandFactory = mock(CommandFactory.class);
        when(commandFactory.getCommand("x")).thenReturn(command);
        when(commandFactory.getCommand("y")).thenReturn(command);

        final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
        final SimpleCallback callback = new SimpleCallback(commandFactory,
                graphicsDevice);

        final Controller controller = mock(Controller.class);

        final Component component1 = mock(Component.class);
        when(component1.getIdentifier()).thenReturn(Identifier.Axis.X);

        final Event event1 = new Event();
        event1.set(component1, 0, 0);

        final Component component2 = mock(Component.class);
        when(component2.getIdentifier()).thenReturn(Identifier.Axis.Y);

        final Event event2 = new Event();
        event2.set(component2, 0, 0);

        // when
        callback.onNewEvent(null, controller, event1);
        callback.removeController(controller);
        callback.onNewEvent(null, controller, event2);

        // then
        assertThat(callback.containsCommandsFor(controller)).isTrue();
    }

    @Test
    public void add_controller() throws Exception {

        // given
        final ICommand command = mock(ICommand.class);
        when(command.isPeriodCommand()).thenReturn(true);

        final CommandFactory commandFactory = mock(CommandFactory.class);
        when(commandFactory.getCommand("x")).thenReturn(command);

        final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
        final SimpleCallback callback = new SimpleCallback(commandFactory,
                graphicsDevice);

        final Controller controller = mock(Controller.class);

        final Component component = mock(Component.class);
        when(component.getIdentifier()).thenReturn(Identifier.Axis.X);

        final Event event = new Event();
        event.set(component, 0, 0);

        // when add command with controller
        callback.onNewEvent(null, controller, event);

        // then
        assertThat(callback.containsCommandsFor(controller)).isTrue();
    }

    @Test
    public void execute_command_for_controller() throws Exception {

        // given
        final ICommand command = mock(ICommand.class);
        final CommandFactory commandFactory = mock(CommandFactory.class);
        when(commandFactory.getCommand("x")).thenReturn(command);

        final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
        final SimpleCallback callback = new SimpleCallback(commandFactory,
                graphicsDevice);

        final Controller controller = mock(Controller.class);

        final Component component = mock(Component.class);
        when(component.getIdentifier()).thenReturn(Identifier.Axis.X);

        final Event event = new Event();
        final float value = 0.333f;
        event.set(component, value, 0);

        // when
        callback.onNewEvent(null, controller, event);

        // then
        verify(command).execute(graphicsDevice, value);
        assertThat(callback.containsCommandsFor(controller)).isFalse();
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

        final Controller controller = mock(Controller.class);

        final Component component = mock(Component.class);
        when(component.getIdentifier()).thenReturn(Identifier.Axis.X);

        final Event event = new Event();
        final float value = 0.333f;
        event.set(component, value, 0);

        // when
        callback.onNewEvent(null, controller, event);

        // then
        verify(command).execute(graphicsDevice, value);
        assertThat(callback.containsCommandsFor(controller)).isTrue();
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

        final Controller controller = mock(Controller.class);

        final Identifier identifier = mock(Identifier.class);
        when(identifier.getName()).thenReturn("pov");

        final Component component = mock(Component.class);
        when(component.getIdentifier()).thenReturn(identifier);

        final Event event = new Event();
        final float value = 0.25f;
        event.set(component, value, 0);

        // when
        callback.onNewEvent(null, controller, event);

        // then
        verify(command).execute(graphicsDevice, 1.0);
    }

    @Test
    public void execute_pov_command_for_controller_then_release()
            throws Exception {

        // given
        final ICommand command = mock(ICommand.class);
        final CommandFactory commandFactory = mock(CommandFactory.class);
        when(commandFactory.getCommand("NP")).thenReturn(command);

        final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
        final SimpleCallback callback = new SimpleCallback(commandFactory,
                graphicsDevice);

        final Controller controller = mock(Controller.class);

        final Identifier identifier = mock(Identifier.class);
        when(identifier.getName()).thenReturn("pov");

        final Component component = mock(Component.class);
        when(component.getIdentifier()).thenReturn(identifier);

        final Event eventPush = new Event();
        eventPush.set(component, 0.25f, 0);

        final Event eventRelease = new Event();
        eventRelease.set(component, 0.0f, 0);

        // when
        callback.onNewEvent(null, controller, eventPush);
        callback.onNewEvent(null, controller, eventRelease);

        // then
        verify(command).execute(graphicsDevice, 1.0);
        verify(command).execute(graphicsDevice, 0.0);
    }

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

        final Controller controller = mock(Controller.class);

        final Identifier identifier = mock(Identifier.class);
        when(identifier.getName()).thenReturn("pov");

        final Component component = mock(Component.class);
        when(component.getIdentifier()).thenReturn(identifier);

        final Event eventPush = new Event();
        eventPush.set(component, 0.25f, 0);

        final Event eventRelease = new Event();
        eventRelease.set(component, 0.0f, 0);

        // when
        callback.onNewEvent(null, controller, eventPush);
        callback.onNewEvent(null, controller, eventRelease);

        // then
        verify(command).execute(graphicsDevice, 1.0);
        verify(command).execute(graphicsDevice, 0.0);
        assertThat(callback.containsCommandsFor(controller)).isFalse();
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

        final Controller controller = mock(Controller.class);

        final Component component = mock(Component.class);
        when(component.getIdentifier()).thenReturn(Identifier.Axis.X);

        final Event event = new Event();
        final float value = 0.333f;
        event.set(component, value, 0);

        // when
        callback.onNewEvent(null, controller, event);
        callback.doPeriodCommands();

        // then
        verify(command, times(2)).execute(graphicsDevice, value);
        assertThat(callback.containsCommandsFor(controller)).isTrue();
    }

    @Test
    public void execute_period_command_for_controller_second_time_and_remove_it()
            throws Exception {

        // given
        final ICommand command = mock(ICommand.class);
        when(command.isPeriodCommand()).thenReturn(true);

        final CommandFactory commandFactory = mock(CommandFactory.class);
        when(commandFactory.getCommand("x")).thenReturn(command);

        final GraphicsDevice graphicsDevice = mock(GraphicsDevice.class);
        final SimpleCallback callback = new SimpleCallback(commandFactory,
                graphicsDevice);

        final Controller controller = mock(Controller.class);

        final Component component = mock(Component.class);
        when(component.getIdentifier()).thenReturn(Identifier.Axis.X);

        final Event event = new Event();
        final float value = 0.333f;
        event.set(component, value, 0);

        // when
        callback.onNewEvent(null, controller, event);
        callback.doPeriodCommands();
        callback.removeController(controller);
        callback.doPeriodCommands();

        // then
        verify(command, times(2)).execute(graphicsDevice, value);
        assertThat(callback.containsCommandsFor(controller)).isFalse();
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

        final Controller controller = mock(Controller.class);

        final Component component = mock(Component.class);
        when(component.getIdentifier()).thenReturn(Identifier.Axis.X);

        final Event event = new Event();
        final float value = 0.333f;
        event.set(component, value, 0); 
        
        final Event secondEvent = new Event();
        final float secondValue = 0.777f;
        secondEvent.set(component, secondValue, 0);

        // when
        callback.onNewEvent(null, controller, event);
        callback.doPeriodCommands();
        callback.onNewEvent(null, controller, secondEvent);
        callback.doPeriodCommands();
        callback.doPeriodCommands();
        
        // then
        verify(command, times(2)).execute(graphicsDevice, value);
        verify(command, times(3)).execute(graphicsDevice, secondValue);
        assertThat(callback.containsCommandsFor(controller)).isTrue();
    }
}
