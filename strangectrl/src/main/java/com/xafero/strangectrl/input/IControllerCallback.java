package com.xafero.strangectrl.input;

import net.java.games.input.Event;

public interface IControllerCallback {
    void onNewEvent(final Event event);

    void doPeriodCommands();

    void controllerRemoved();
}
