package com.xafero.strangectrl.input;

import java.awt.GraphicsDevice;
import java.awt.Robot;
import java.util.Map;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Controller;
import net.java.games.input.Event;

import com.xafero.strangectrl.cmd.Commands;
import com.xafero.strangectrl.cmd.Commands.MouseMoveCmd;
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
        final Component component = event.getComponent();
        final String name = component.getName();

        syso(component, name);
        
        for (final String mapping : cmds.keySet()) {
            if (!mapping.equalsIgnoreCase(name)) {
                continue;
            }
            final ICommand cmd = cmds.get(mapping);
//            cmd.execute(rbt, dev, event.getValue());
            return;
        }

        final int maxMove =5;
        
        if(component.getIdentifier() instanceof Axis) {
            final Axis identifier = (Axis) component.getIdentifier();
            
            if(component.isAnalog()) {
                final MouseMoveCmd cmd = new Commands.MouseMoveCmd(maxMove+" "+identifier.getName());
                
                cmd.execute(rbt, dev, event.getValue());
            }
        }
    }

    private void syso(final Component component, final String name) {
        System.out.printf("component class = %s%n\t"
                + "name = %s%n\t"
                + "isAnalog = %b%n\t"
                + "isRelative = %b%n\t"
                + "identifier name = %s%n\t"
                + "identifierClass = %s%n\t"
                + "pollData = %f%n\t"
                + "deadZone = %f%n\t"
                + "rounded = %d%n",
                component.getClass(), name, component.isAnalog(), component.isRelative(), component.getIdentifier().getName(),component.getIdentifier().getClass(), component.getPollData(), component.getDeadZone(),(int)((2 - (1 - component.getPollData())) * 100) / 2);
    }

}
