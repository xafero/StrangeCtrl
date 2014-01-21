package com.xafero.strangectrl.input;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Collection;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class ControllerPoller extends TimerTask {
	private final Collection<Controller> controllers;
	private final IControllerCallback callback;
	private Controller controller;
	private boolean started = false;
	private final long period;
	private final Timer daemon = new Timer(true);

	public ControllerPoller(final Collection<Controller> controllers,
			final IControllerCallback callback, final long period) {
		this.controllers = new HashSet<>(checkNotNull(controllers));
		this.callback = checkNotNull(callback);
		checkArgument(period > 0);
		this.period = period;
	}

	public void start() {
		checkState(canRun(), "Cannot run! Controllers is empty!");
		checkState(!started, "Cannot start twice!");

		controller = controllers.iterator().next();
		started = true;
		daemon.scheduleAtFixedRate(this, 0, period);
	}

	public boolean canRun() {
		return !controllers.isEmpty();
	}

	@Override
	public void run() {
		checkState(controller != null, "Did not start!");

		callback.doPeriodCommands();

		if (controller.poll()) {

			final EventQueue queue = controller.getEventQueue();
			final Event event = new Event();
			while (queue.getNextEvent(event)) {
				callback.onNewEvent(event);
			}
		} else {

			// controller is no longer available
			callback.controllerRemoved();
			controllers.remove(controller);
			if (canRun()) {
				controller = controllers.iterator().next();
			} else {
				stop();
			}
		}
	}

	public void stop() {
		checkState(started, "Cannot stop before starting!");
		daemon.cancel();
		daemon.purge();
		started = false;
	}
}