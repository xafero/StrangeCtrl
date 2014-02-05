package com.xafero.strangectrl.input;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.TimerTask;

import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class ControllerPoller extends TimerTask {
    private final Collection<Controller> controllers = new HashSet<>();
    private final IControllerCallback callback;
    private final ControllersRefresher controllersRefresher;
    private Controller controller;

    public ControllerPoller(final IControllerCallback callback,
            final ControllersRefresher controllersRefresher) {
        this.callback = checkNotNull(callback);
        this.controllersRefresher = checkNotNull(controllersRefresher);
    }

    private boolean canRun() {
        return !controllers.isEmpty();
    }

    @Override
    public void run() {
        synchronized (controllers) {
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

    public void refreshPads() {
        synchronized (controllers) {
            controllers.clear();
            controllersRefresher.refreshNextTime();
        }
    }
}
