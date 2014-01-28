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
	private static final String RELEASE_POV = "RELEASE_POV";
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

		final String configName = transformIdentifier(identifier,
				event.getValue());
		final ICommand command = commandFactory.getCommand(configName);
		if (command != null) {
			double value = event.getValue();

			if ("pov".equalsIgnoreCase(identifier)) {
				value = 1.0;
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

		} else if (RELEASE_POV.equalsIgnoreCase(configName)) {
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

	private String transformIdentifier(final String identifier,
			final float value) {
		switch (identifier) {
		case "0":
			return "A";
		case "1":
			return "B";
		case "2":
			return "X";
		case "3":
			return "Y";
		case "4":
			return "LB";
		case "5":
			return "RB";
		case "6":
			return "BACK";
		case "7":
			return "START";
		case "8":
			return "LS";
		case "9":
			return "RS";
		case "pov":
			return findPov(value);
		default:
			return identifier;
		}
	}

	private String findPov(final double value) {
		if (value == 0.125) {
			return "NWP";
		} else if (value == 0.25) {
			return "NP";
		} else if (value == 0.375) {
			return "NEP";
		} else if (value == 0.5) {
			return "EP";
		} else if (value == 0.625) {
			return "SEP";
		} else if (value == 0.75) {
			return "SP";
		} else if (value == 0.875) {
			return "SWP";
		} else if (value == 1) {
			return "WP";
		} else if (value == 0) {
			return RELEASE_POV;
		}

		logger.error("Cannot find this value in POV : " + value);
		throw new RuntimeException("Cannot find this value in POV : " + value);
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

		@Override
		public String toString() {
			return "CommandLastValue [value=" + value + ", command="
					+ command.getClass().getSimpleName() + "]";
		}

	}
}
