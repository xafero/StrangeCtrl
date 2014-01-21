package com.xafero.strangectrl.input;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.TimerTask;

import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class ControllerPoller extends TimerTask {
	private final ControllersRefresher refresher;
	private final Controller controller;
	private final IControllerCallback callback;

	public ControllerPoller(final ControllersRefresher refresher,
			final Controller controller, final IControllerCallback callback) {
		this.refresher = checkNotNull(refresher);
		this.controller = checkNotNull(controller);
		this.callback = checkNotNull(callback);
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
			callback.controllerRemoved();
			refresher.controllerNotAvailable(controller);
			cancel();
		}
	}

}