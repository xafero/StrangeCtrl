package com.xafero.strangectrl.input;

import java.util.Timer;
import java.util.TimerTask;

import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class ControllerPoller extends TimerTask {
	private final Controller controller;
	private final long period;
	private final IControllerCallback callback;
	private Timer daemon;

	public ControllerPoller(final Controller controller, final long period,
			final IControllerCallback callback) {
		this.controller = controller;
		this.period = period;
		this.callback = callback;
	}

	public void start() {
		daemon = new Timer(true);
		daemon.scheduleAtFixedRate(this, period, period);
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
			// TODO
		}
	}

	public void stop() {
		daemon.cancel();
		daemon.purge();
	}
}