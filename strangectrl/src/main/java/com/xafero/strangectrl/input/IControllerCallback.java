package com.xafero.strangectrl.input;

import net.java.games.input.Controller;
import net.java.games.input.Event;

public interface IControllerCallback {
    void onNewEvent(final ControllerPoller poller,
            final Controller controller,
            final Event event);

    void removeController(final Controller controller);

    void doPeriodCommands();
}
