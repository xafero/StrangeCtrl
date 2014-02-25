package com.xafero.strangectrl;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.LoggerFactory;

import pl.grzeslowski.strangectrl.cmd.CommandFactory;
import pl.grzeslowski.strangectrl.config.ConfigReader;
import pl.grzeslowski.strangectrl.config.Configuration;

import com.xafero.strangectrl.awt.ControllersRefreshListener;
import com.xafero.strangectrl.awt.DesktopUtils;
import com.xafero.strangectrl.awt.ExitListener;
import com.xafero.strangectrl.awt.ResourceUtils;
import com.xafero.strangectrl.awt.TrayPopupMenu;
import com.xafero.strangectrl.input.ControllerPoller;
import com.xafero.strangectrl.input.ControllersRefresher;
import com.xafero.strangectrl.input.IControllerCallback;
import com.xafero.strangectrl.input.InputUtils;
import com.xafero.strangectrl.input.SimpleCallback;
import com.xafero.strangectrl.input.TimeRunner;

/**
 * The main entry point
 */
public class App implements ControllersRefreshListener, ExitListener {
    private static final long MAX_WAIT_TIME_TO_REFRESH_CONTROLLERS = 10;
    private static final org.slf4j.Logger logger = LoggerFactory
            .getLogger(App.class);
    public static final String RESOURCES_PATH = "/";
    private static final String LOG4J_PROPERTIES = "log4j.properties";
    private static final String TRAY_ICON = "console-controller2.png";
    private final static String TIP = "Strange Control";
    private static final long PERIOD = 10;

    private final InputUtils inputUtils;
    private final GraphicsDevice graphicsDevice;
    private ControllerPoller controllerPoller;
    private SystemTray tray;
    private TrayIcon trayIcon;
    private TimeRunner timeRunner;
    private final ConfigReader configReader = new ConfigReader();

    public App() {
        AtomicReference<GraphicsDevice> devRef;
        final Robot robot = DesktopUtils
                .createRobot(devRef = new AtomicReference<GraphicsDevice>());
        graphicsDevice = devRef.get();
        inputUtils = new InputUtils(robot);
    }

    public static void main(final String[] args) {
        logger.info("Loading SLF4J properties");
        loadSlf4jProperties();

        // start app
        new App().start();
    }

    private static void loadSlf4jProperties() {
        try {
            final Properties props = new Properties();
            final InputStream configStream = App.class
                    .getResourceAsStream(App.RESOURCES_PATH + LOG4J_PROPERTIES);
            props.load(configStream);
            PropertyConfigurator.configure(props);

            configStream.close();
        } catch (final IOException e) {
            logger.error("Cannot load path for SL4J! Path = {%s}",
                    App.RESOURCES_PATH + LOG4J_PROPERTIES);
        }
    }

    private void start() {
        logger.info("Starting!");

        // load tray
        loadTray();

        // load conf
        final Configuration configuration = loadConfiguration();

        logger.info(String.format("Got %s buttons.", configuration.getButtons()
                .size()));

        startControllerPoller(configuration);
    }

    private void startControllerPoller(final Configuration configuration) {
        final CommandFactory commandFactory = new CommandFactory(inputUtils,
                configuration);

        final IControllerCallback callback = new SimpleCallback(commandFactory,
                graphicsDevice);
        final ControllersRefresher controllersRefresher = new ControllersRefresher(
                inputUtils, MAX_WAIT_TIME_TO_REFRESH_CONTROLLERS, PERIOD);
        controllerPoller = new ControllerPoller(callback, controllersRefresher);

        timeRunner = new TimeRunner(PERIOD, controllerPoller);

        timeRunner.start();
    }

    private void loadTray() {
        try {
            tray = SystemTray.getSystemTray();
            trayIcon = DesktopUtils.createTrayIcon(loadTrayIconImage(), TIP,
                    new TrayPopupMenu(this, this));
            tray.add(trayIcon);
        } catch (final AWTException e1) {
            logger.error("Cannot create tray!");
        }
    }

    private Image loadTrayIconImage() {
        try {
            final InputStream imageStream = App.class
                    .getResourceAsStream(RESOURCES_PATH + TRAY_ICON);
            final Image loadImage = ResourceUtils.loadImage(imageStream);
            imageStream.close();
            return loadImage;
        } catch (final IOException e) {
            logger.error("Cannot load tray image!", e);
            throw new RuntimeException(e);
        }
    }

    private Configuration loadConfiguration() {
        return configReader.loadConfiguration();
    }

    @Override
    public void exit() {
        tray.remove(trayIcon);

        timeRunner.stop();
    }

    @Override
    public void refreshControllers() {
        controllerPoller.refreshPads();
    }
}
