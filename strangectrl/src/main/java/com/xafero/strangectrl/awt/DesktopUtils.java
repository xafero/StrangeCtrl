package com.xafero.strangectrl.awt;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Robot;
import java.awt.TrayIcon;
import java.util.concurrent.atomic.AtomicReference;

public class DesktopUtils {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(DesktopUtils.class);

    public static GraphicsDevice getMouseScreen() {
        return MouseInfo.getPointerInfo().getDevice();
    }

    public Point getMousePos(final GraphicsDevice dev) {
        final Point pos = MouseInfo.getPointerInfo().getLocation();
        final GraphicsConfiguration cfg = dev.getDefaultConfiguration();
        final int newX = pos.x - cfg.getBounds().x;
        final int newY = pos.y - cfg.getBounds().y;

        return new Point(newX, newY);
    }

    public static TrayIcon createTrayIcon(final Image img, final String tip,
            final PopupMenu menu) {
        final TrayIcon icon = new TrayIcon(img, tip, menu);
        icon.setImageAutoSize(true);

        return icon;
    }

    public static GraphicsDevice getDevice(final int screenNo)
            throws AWTException {
        final GraphicsEnvironment env = GraphicsEnvironment
                .getLocalGraphicsEnvironment();

        return env.getScreenDevices()[screenNo];
    }

    public static Robot createRobot(final GraphicsDevice device) {
        try {
            return new Robot(device);
        } catch (final AWTException e) {
            logger.error("Cannot create robot!", e);
            throw new RuntimeException("createRobot", e);
        }
    }

    public static Robot createRobot(
            final AtomicReference<GraphicsDevice> deviceRef) {
        final GraphicsDevice device = getMouseScreen();
        deviceRef.set(device);

        return createRobot(device);
    }
}
