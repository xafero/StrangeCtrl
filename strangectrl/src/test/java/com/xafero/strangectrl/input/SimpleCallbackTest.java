package com.xafero.strangectrl.input;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
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
}
