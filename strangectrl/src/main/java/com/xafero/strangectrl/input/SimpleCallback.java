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

		final Component component = event.getComponent();
		final String identifier = component.getIdentifier().getName();
		final double value = event.getValue();
		final ICommand command = commandFactory.getCommand(identifier, value);

		if (command != null) {
			if ("pov".equalsIgnoreCase(identifier)) {
				lastPovCommand = command;
			}

			// execute command
			command.execute(graphicsDevice, value);

			// add period command
			if (command.isPeriodCommand()) {
				removeCommand(command);

				if (value != 0.0) {
					periodExecutionCommands.add(new CommandLastValue(value,
							command));
				}
			} else {
				if (value != 0.0) {
					commandsInExecution
					.add(new CommandLastValue(value, command));
				} else {
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
			}

		} else if ("pov".equalsIgnoreCase(identifier) && value == 0.0) {
			if (lastPovCommand.isPeriodCommand()) {
				removeCommand(lastPovCommand);
			} else {
				lastPovCommand.execute(graphicsDevice, 0.0);
			}
			lastPovCommand = null;
		}
	}

	@Override
	public synchronized void doPeriodCommands() {
		for (final CommandLastValue commandLastValue : periodExecutionCommands) {
			commandLastValue.command.execute(graphicsDevice,
					commandLastValue.value);
		}
	}

	@Override
	public void controllerRemoved() {
		synchronized (periodExecutionCommands) {
			for (final CommandLastValue command : periodExecutionCommands) {
				command.command.execute(graphicsDevice, 0.0);
			}

			periodExecutionCommands.clear();
		}

		synchronized (commandsInExecution) {
			for (final CommandLastValue command : commandsInExecution) {
				command.command.execute(graphicsDevice, 0.0);
			}

			commandsInExecution.clear();
		}
	}

	private void removeCommand(final ICommand command) {
		synchronized (periodExecutionCommands) {
			for (final Iterator<CommandLastValue> it = periodExecutionCommands
					.iterator(); it.hasNext();) {
				final CommandLastValue next = it.next();

				if (next.command.equals(command)) {
					command.execute(graphicsDevice, 0.0);
					it.remove();
					break;
				}
			}
		}
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
