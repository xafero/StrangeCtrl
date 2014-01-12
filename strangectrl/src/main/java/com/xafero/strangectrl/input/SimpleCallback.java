package com.xafero.strangectrl.input;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.GraphicsDevice;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;

import org.slf4j.LoggerFactory;

import pl.grzeslowski.strangectrl.cmd.CommandFactory;

import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.ControllerPoller.IControllerCallback;

public class SimpleCallback implements IControllerCallback {
    private static final org.slf4j.Logger logger = LoggerFactory
            .getLogger(SimpleCallback.class);
    private final CommandFactory commandFactory;
    private final GraphicsDevice graphicsDevice;

    public SimpleCallback(final CommandFactory commandFactory,
            final GraphicsDevice graphicsDevice) {
        this.commandFactory = checkNotNull(commandFactory);
        this.graphicsDevice = checkNotNull(graphicsDevice);
    }

    @Override
    public void onNewEvent(final ControllerPoller poller,
            final Controller controller, final Event event) {
        final Component component = event.getComponent();
        final String identifier = component.getIdentifier().getName();

        logger.info("Got event with identifier : " + identifier + " ("
                + event.getValue() + ") " + transformIdentifier(
                        identifier, event.getValue()));

        final ICommand command = commandFactory.getCommand(transformIdentifier(
                identifier, event.getValue()));
        if (command != null) {
            command.execute(graphicsDevice, event.getValue());
        }
    }

    private String transformIdentifier(final String identifier,
            final float value) {
        switch (identifier) {
        case "0":
            return "A";
        case "1":
            return "B";
        case "2":
            return "X";
        case "3":
            return "Y";
        case "4":
            return "LB";
        case "5":
            return "RB";
        case "6":
            return "BACK";
        case "7":
            return "START";
        case "8":
            return "LS";
        case "9":
            return "RS";
        default:
            return identifier;
        }
    }

}
