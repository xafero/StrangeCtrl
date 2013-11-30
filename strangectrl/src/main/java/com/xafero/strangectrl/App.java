package com.xafero.strangectrl;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.Event;

import com.xafero.strangectrl.awt.DesktopUtils;
import com.xafero.strangectrl.awt.ResourceUtils;
import com.xafero.strangectrl.cmd.ConfigUtils;
import com.xafero.strangectrl.cmd.ICommand;
import com.xafero.strangectrl.input.ControllerPoller;
import com.xafero.strangectrl.input.ControllerPoller.IControllerCallback;
import com.xafero.strangectrl.input.InputUtils;

/**
 * The main entry point
 */
public class App {

	private static final String EXIT_STR = "Exit";
	private static final String CFG_FILE = "config.xml";

	public static void main(String[] args) throws IOException, AWTException {

		AtomicReference<GraphicsDevice> devRef;
		final Robot rbt = DesktopUtils
				.createRobot(devRef = new AtomicReference<GraphicsDevice>());
		final GraphicsDevice dev = devRef.get();

		final Map<String, ICommand> cmds = ConfigUtils.loadCommands(CFG_FILE);

		SystemTray tray = SystemTray.getSystemTray();
		Image img = ResourceUtils.loadImage("console-controller2.png");
		String tip = "Strange Control";

		PopupMenu menu = new PopupMenu("test2!");
		menu.add(EXIT_STR);
		menu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand() == EXIT_STR)
					System.exit(0);
			}
		});

		tray.add(DesktopUtils.createTrayIcon(img, tip, menu));

		List<Controller> pads = InputUtils.getControllers(Type.GAMEPAD);
		IControllerCallback callback = new IControllerCallback() {

			@Override
			public void onNewEvent(ControllerPoller poller,
					Controller controller, Event event) {
				String name = event.getComponent().getName();

				for (String mapping : cmds.keySet()) {
					if (!mapping.equalsIgnoreCase(name))
						continue;
					ICommand cmd = cmds.get(mapping);
					cmd.execute(rbt, dev, event.getValue());
					return;
				}
			}
		};
		ControllerPoller poller = new ControllerPoller(pads, 100, callback);
		poller.start();
	}
}