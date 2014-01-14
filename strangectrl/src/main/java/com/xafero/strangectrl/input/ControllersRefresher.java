package com.xafero.strangectrl.input;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;

import org.slf4j.LoggerFactory;

public class ControllersRefresher extends TimerTask {
    private static final org.slf4j.Logger logger = LoggerFactory
            .getLogger(ControllersRefresher.class);
    private final static int TIMER_TIME = 500;
    private final ControllerPoller controllerPoller;
    private final InputUtils inputUtils;
    private Timer daemon;

    public ControllersRefresher(final ControllerPoller controllerPoller,
            final InputUtils inputUtils) {
        this.controllerPoller = checkNotNull(controllerPoller);
        this.inputUtils = checkNotNull(inputUtils);
    }

    public void start() {
        daemon = new Timer(true);
        daemon.scheduleAtFixedRate(this, TIMER_TIME, TIMER_TIME);
    }

    public void stop() {
        daemon.cancel();
        daemon.purge();
    }

    @Override
    public void run() {
        final Set<Controller> newControllers = inputUtils
                .getControllers(Type.GAMEPAD);

        if (newControllers != null && !newControllers.isEmpty()) {
            controllerPoller.updateControllers(newControllers);
        }
    }
}
