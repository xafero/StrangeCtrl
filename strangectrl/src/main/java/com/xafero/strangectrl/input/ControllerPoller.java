package com.xafero.strangectrl.input;

import java.util.Timer;
import java.util.TimerTask;

import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class ControllerPoller extends TimerTask {
	private final Iterable<Controller> controllers;
	private final long period;
	private final IControllerCallback callback;

	private Timer daemon;

	public ControllerPoller(Iterable<Controller> controllers, long period,
			IControllerCallback callback) {
		this.controllers = controllers;
		this.period = period;
		this.callback = callback;
	}

	public void start() {
		daemon = new Timer(true);
		daemon.scheduleAtFixedRate(this, period, period);
	}

	@Override
	public void run() {
		for (Controller controller : controllers) {
			if (!controller.poll())
				continue;
			EventQueue queue = controller.getEventQueue();
			Event event = new Event();
			while (queue.getNextEvent(event))
				callback.onNewEvent(this, controller, event);
		}
	}

	public void stop() {
		daemon.cancel();
		daemon.purge();
	}

	public static interface IControllerCallback {
		void onNewEvent(ControllerPoller poller, Controller controller,
				Event event);
	}
}