package com.xafero.strangectrl.input;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.GraphicsDevice;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.java.games.input.Component;
import net.java.games.input.Event;
import pl.grzeslowski.strangectrl.cmd.CommandFactory;

import com.google.common.base.Objects;
import com.xafero.strangectrl.cmd.ICommand;

public class SimpleCallback implements IControllerCallback {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(SimpleCallback.class);

    private final CommandFactory commandFactory;
    private final GraphicsDevice graphicsDevice;
    private final Set<CommandLastValue> periodExecutionCommands = Collections
            .synchronizedSet(new HashSet<CommandLastValue>());
    private final Set<CommandLastValue> commandsInExecution = Collections
            .synchronizedSet(new HashSet<CommandLastValue>());
    private ICommand lastPovCommand;

    public SimpleCallback(final CommandFactory commandFactory,
            final GraphicsDevice graphicsDevice) {
        this.commandFactory = checkNotNull(commandFactory);
        this.graphicsDevice = checkNotNull(graphicsDevice);
    }

    @Override
    public synchronized void onNewEvent(final Event event) {
        checkNotNull(event);

        final double value = event.getValue();
        final Component component = event.getComponent();
        final String identifier = component.getIdentifier().getName();

        final ICommand command = commandFactory.getCommand(identifier, value);
        if (command != null) {
            if (!command.isPeriodCommand()) {
                onNormalCommandEvent(value, command);
            } else {
                onPeriodCommandEvent(value, command);
            }
        }

        povSupport(identifier, command);
    }

    private void povSupport(final String identifier, final ICommand command) {

        // set last command if pov will be executed
        if ("pov".equalsIgnoreCase(identifier) && command != null) {
            lastPovCommand = command;
        }

        // remove last command in pov is there is releasing
        if ("pov".equalsIgnoreCase(identifier) && lastPovCommand != null
                && command == null) {
            if (lastPovCommand.isPeriodCommand()) {
                removePeriodCommand(lastPovCommand);
            } else {
                turnOffCommand(lastPovCommand);
                removeCommand(lastPovCommand);
            }
            lastPovCommand = null;
        }
    }

    private void onNormalCommandEvent(final double value, final ICommand command) {
        onCommand(value, command, commandsInExecution, true);
    }

    private void onPeriodCommandEvent(final double value, final ICommand command) {
        onCommand(value, command, periodExecutionCommands, false);
    }

    private void onCommand(final double value, final ICommand command,
            final Set<CommandLastValue> set, final boolean normalCommand) {

        // execute command
        if (normalCommand) {
            command.execute(graphicsDevice, value);
        } else {
            command.executePeriodCommand(graphicsDevice, value);
        }

        // remove old command
        synchronized (set) {
            for (final Iterator<CommandLastValue> it = set.iterator(); it
                    .hasNext();) {
                final CommandLastValue next = it.next();

                if (equal(command, next.command)) {
                    it.remove();
                    break;
                }
            }
        }

        // add command
        if (value != 0.0) {
            set.add(new CommandLastValue(value, command));
        }
    }

    private void removeCommand(final ICommand command) {
        synchronized (commandsInExecution) {
            for (final Iterator<CommandLastValue> it = commandsInExecution
                    .iterator(); it.hasNext();) {
                final CommandLastValue next = it.next();

                if (equal(command, next.command)) {
                    it.remove();
                    break;
                }
            }
        }
    }

    @Override
    public synchronized void doPeriodCommands() {
        for (final CommandLastValue commandLastValue : periodExecutionCommands) {
            commandLastValue.command.executePeriodCommand(graphicsDevice,
                    commandLastValue.value);
        }
    }

    @Override
    public void controllerRemoved() {
        synchronized (periodExecutionCommands) {
            for (final CommandLastValue command : periodExecutionCommands) {
                turnOffCommand(command.command);
            }

            periodExecutionCommands.clear();
        }

        synchronized (commandsInExecution) {
            for (final CommandLastValue command : commandsInExecution) {
                turnOffCommand(command.command);
            }

            commandsInExecution.clear();
        }
    }

    private void removePeriodCommand(final ICommand command) {
        synchronized (periodExecutionCommands) {
            for (final Iterator<CommandLastValue> it = periodExecutionCommands
                    .iterator(); it.hasNext();) {
                final CommandLastValue next = it.next();

                if (next.command.equals(command)) {
                    it.remove();
                    break;
                }
            }
        }
    }

    private void turnOffCommand(final ICommand command) {
        command.execute(graphicsDevice, 0.0);
    }

    private class CommandLastValue {
        private final double value;
        private final ICommand command;

        public CommandLastValue(final double value, final ICommand command) {
            this.value = value;
            this.command = command;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value, command);
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof CommandLastValue) {
                final CommandLastValue tmp = (CommandLastValue) obj;

                return equal(value, tmp.value) && equal(command, tmp.command);
            } else {
                return false;
            }
        }
    }
}
