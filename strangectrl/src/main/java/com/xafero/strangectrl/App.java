package com.xafero.strangectrl;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.LoggerFactory;

import pl.grzeslowski.strangectrl.cmd.CommandFactory;
import pl.grzeslowski.strangectrl.config.ConfigLoader;
import pl.grzeslowski.strangectrl.config.Configuration;
import pl.grzeslowski.strangectrl.config.XStreamConfigLoader;

import com.xafero.strangectrl.awt.DesktopUtils;
import com.xafero.strangectrl.awt.ResourceUtils;
import com.xafero.strangectrl.input.ControllerPoller;
import com.xafero.strangectrl.input.ControllersRefresher;
import com.xafero.strangectrl.input.IControllerCallback;
import com.xafero.strangectrl.input.InputUtils;
import com.xafero.strangectrl.input.SimpleCallback;

/**
 * The main entry point
 */
public class App {
	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(App.class);
	private static final String LOG4J_PROPERTIES_PATH = "src/main/resources/log4j.properties";
	private static final String EXIT_STR = "Exit";
	private static final String CFG_FILE = "src/main/resources/new_config.xml";
	private static final long PERIOD = 10;
	private final ConfigLoader configLoader = new XStreamConfigLoader();
	private final InputUtils inputUtils;
	private final GraphicsDevice graphicsDevice;
	private final Robot robot;

	public App() {
		AtomicReference<GraphicsDevice> devRef;
		robot = DesktopUtils
				.createRobot(devRef = new AtomicReference<GraphicsDevice>());
		graphicsDevice = devRef.get();
		inputUtils = new InputUtils(robot);
	}

	public static void main(final String[] args) {
		loadSlf4jProperties();

		final SystemTray tray = SystemTray.getSystemTray();
		final Image img = loadTrayIconImage();
		final String tip = "Strange Control";

		final PopupMenu menu = new PopupMenu("test2!");
		menu.add(EXIT_STR);
		menu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (e.getActionCommand() == EXIT_STR) {
					System.exit(0);
				}
			}
		});

		try {
			tray.add(DesktopUtils.createTrayIcon(img, tip, menu));
		} catch (final AWTException e1) {
			throw new RuntimeException(e1);
		}

		// start app
		new App().start();
	}

	private static Image loadTrayIconImage() {
		try {
			final InputStream imageStream = App.class
					.getResourceAsStream("/resources/console-controller2.png");
			final Image loadImage = ResourceUtils.loadImage(imageStream);
			imageStream.close();
			return loadImage;
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void loadSlf4jProperties() {
		try {
			final Properties props = new Properties();
			final InputStream configStream = App.class
					.getResourceAsStream("/resources/log4j.properties");
			props.load(configStream);
			PropertyConfigurator.configure(props);

			configStream.close();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void start() {

		// load conf
		final Configuration configuration = loadConfiguration();

		// get all commands
		final CommandFactory commandFactory = new CommandFactory(inputUtils,
				configuration);

		final Set<Controller> pads = inputUtils.getControllers(Type.GAMEPAD);
		final IControllerCallback callback = new SimpleCallback(commandFactory,
				graphicsDevice);
		final ControllersRefresher controllersRefresher = new ControllersRefresher(
				inputUtils);
		final ControllerPoller poller = new ControllerPoller(pads, callback,
				controllersRefresher, PERIOD);
		poller.start();
	}

	private Configuration loadConfiguration() {
		try {
			final InputStream configStream = App.class
					.getResourceAsStream("/resources/new_config.xml");
			final String readedFile = readConfigFile(configStream);
			configStream.close();

			return configLoader.loadXml(readedFile);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String readConfigFile(final InputStream configStream) {
		BufferedReader in = null;
		String readedFile = "";
		try {
			in = new BufferedReader(new InputStreamReader(configStream));
			String str;
			final StringBuilder builder = new StringBuilder();
			while ((str = in.readLine()) != null) {
				builder.append(str);
			}

			readedFile = builder.toString();
		} catch (final IOException e) {
			logger.error("Error in reading file!", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (final IOException e) {
					logger.error("Error in reading file (closing in final)!", e);
				}
			}
		}
		return readedFile;
	}
}