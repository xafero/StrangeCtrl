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
	private final Collection<Controller> controllers = new HashSet<>();
	private final IControllerCallback callback;
	private final ControllersRefresher controllersRefresher;
	private Controller controller;
	private boolean started = false;
	private final long period;
	private final Timer daemon = new Timer(true);

	public ControllerPoller(final IControllerCallback callback,
			final ControllersRefresher controllersRefresher, final long period) {
		this.callback = checkNotNull(callback);
		this.controllersRefresher = checkNotNull(controllersRefresher);
		checkArgument(period > 0);
		this.period = period;
	}

	public void start() {
		checkState(!started, "Cannot start twice!");

		started = true;
		daemon.scheduleAtFixedRate(this, 0, period);
	}

	public boolean canRun() {
		return !controllers.isEmpty();
	}

	@Override
	public void run() {
		synchronized (controllers) {
			checkState(started, "Did not start!");

			if (canRun()) {
				if (controller == null) {
					controller = controllers.iterator().next();
				}
				runForController();
			} else {

				// need to refresh controllers
				refreshControllers();
			}
		}
	}

	private void runForController() {
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
			controller = null;
		}
	}

	private void refreshControllers() {
		controllers.addAll(controllersRefresher.getController());
	}

	public void stop() {
		checkState(started, "Cannot stop before starting!");
		daemon.cancel();
		daemon.purge();
		started = false;
	}

	public void refreshPads() {
		synchronized (controllers) {
			controllers.clear();
			controllersRefresher.refreshNextTime();
		}
	}
}