package com.xafero.strangectrl.input;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class ControllerPoller extends TimerTask {
    private final Set<Controller> controllers;
    private final long period;
    private final IControllerCallback callback;

    private Timer daemon;

    public ControllerPoller(final Set<Controller> controllers,
            final long period,
            final IControllerCallback callback) {
        this.controllers = Collections.synchronizedSet(new HashSet<Controller>(
                controllers));
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

        // Set<Controller> controllers = InputUtils
        // .getControllers(Type.GAMEPAD);
        synchronized (controllers) {
            for (final Controller controller : controllers) {
                if (controller.poll()) {
                    final EventQueue queue = controller.getEventQueue();
                    final Event event = new Event();
                    while (queue.getNextEvent(event)) {
                        callback.onNewEvent(this, controller, event);
                    }
                } else {

                    // controller is no longer available
                    controllers.remove(controller);
                    callback.removeController(controller);
                }
            }
        }
    }

    public void stop() {
        daemon.cancel();
        daemon.purge();
    }

    public static interface IControllerCallback {
        void onNewEvent(final ControllerPoller poller,
                final Controller controller,
                final Event event);

        void removeController(final Controller controller);

        void doPeriodCommands();
    }
}