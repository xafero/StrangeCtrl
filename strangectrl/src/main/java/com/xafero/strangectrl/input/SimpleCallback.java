package com.xafero.strangectrl.input;

import java.awt.GraphicsDevice;
import java.awt.Robot;
import java.util.Map;

import net.java.games.input.Controller;
import net.java.games.input.Event;

import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.ControllerPoller.IControllerCallback;

public class SimpleCallback implements IControllerCallback {

    private final Map<String, ICommand> cmds;
    private final Robot rbt;
    private final GraphicsDevice dev;

    public SimpleCallback(final Map<String, ICommand> cmds, final Robot rbt,
            final GraphicsDevice dev) {
        this.cmds = cmds;
        this.rbt = rbt;
        this.dev = dev;
    }

    @Override
    public void onNewEvent(final ControllerPoller poller,
            final Controller controller, final Event event) {
        final String name = event.getComponent().getName();

        for (final String mapping : cmds.keySet()) {
            if (!mapping.equalsIgnoreCase(name)) {
                continue;
            }
            final ICommand cmd = cmds.get(mapping);
            cmd.execute(rbt, dev, event.getValue());
            return;
        }

        System.out.println("not known : " + name);
    }

}
