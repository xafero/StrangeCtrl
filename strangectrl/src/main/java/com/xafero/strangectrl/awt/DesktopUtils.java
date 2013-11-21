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

	public static GraphicsDevice getMouseScreen() {
		return MouseInfo.getPointerInfo().getDevice();
	}

	public static Point getMousePos(GraphicsDevice dev) {
		Point pos = MouseInfo.getPointerInfo().getLocation();
		GraphicsConfiguration cfg = dev.getDefaultConfiguration();
		int newX = pos.x - cfg.getBounds().x;
		int newY = pos.y - cfg.getBounds().y;
		return new Point(newX, newY);
	}

	public static TrayIcon createTrayIcon(Image img, String tip, PopupMenu menu) {
		TrayIcon icon = new TrayIcon(img, tip, menu);
		icon.setImageAutoSize(true);
		return icon;
	}

	public static GraphicsDevice getDevice(int screenNo) throws AWTException {
		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getScreenDevices()[screenNo];
		return device;
	}

	public static Robot createRobot(GraphicsDevice device) {
		try {
			return new Robot(device);
		} catch (AWTException e) {
			throw new RuntimeException("createRobot", e);
		}
	}

	public static Robot createRobot(AtomicReference<GraphicsDevice> deviceRef) {
		GraphicsDevice device = getMouseScreen();
		deviceRef.set(device);
		return createRobot(device);
	}
}