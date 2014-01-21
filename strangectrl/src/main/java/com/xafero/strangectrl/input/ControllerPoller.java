package com.xafero.strangectrl.input;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Timer;
import java.util.TimerTask;

import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class ControllerPoller extends TimerTask {
	private final ControllersRefresher refresher;
	private final Controller controller;
	private final long period;
	private final IControllerCallback callback;
	private final Timer daemon;
	private boolean started = false;

	public ControllerPoller(final ControllersRefresher refresher,
			final Controller controller, final long period,
			final IControllerCallback callback) {
		this.refresher = checkNotNull(refresher);
		this.controller = checkNotNull(controller);
		checkArgument(period > 0, "Period need to be greater than 0!");
		this.period = period;
		this.callback = checkNotNull(callback);
		daemon = new Timer(true);
	}

	public void start() {
		checkState(!started, "Cannot start twice!");
		daemon.scheduleAtFixedRate(this, 0, period);
		started = true;
	}

	@Override
	public void run() {
		callback.doPeriodCommands();

		if (controller.poll()) {
			final EventQueue queue = controller.getEventQueue();
			final Event event = new Event();
			while (queue.getNextEvent(event)) {
				callback.onNewEvent(event);
			}
		} else {

			// controller is no longer available
			stop();
			callback.controllerRemoved();
			refresher.controllerNotAvaible(controller);
		}
	}

	public void stop() {
		checkState(started, "Cannot stop before starting!");
		daemon.cancel();
		daemon.purge();
	}
}