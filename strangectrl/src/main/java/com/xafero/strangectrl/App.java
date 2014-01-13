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
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;

import org.apache.log4j.PropertyConfigurator;

import pl.grzeslowski.strangectrl.cmd.CommandFactory;
import pl.grzeslowski.strangectrl.config.ConfigLoader;
import pl.grzeslowski.strangectrl.config.Configuration;
import pl.grzeslowski.strangectrl.config.XStreamConfigLoader;

import com.xafero.strangectrl.awt.DesktopUtils;
import com.xafero.strangectrl.awt.ResourceUtils;
import com.xafero.strangectrl.input.ControllerPoller;
import com.xafero.strangectrl.input.ControllerPoller.IControllerCallback;
import com.xafero.strangectrl.input.InputUtils;
import com.xafero.strangectrl.input.SimpleCallback;

/**
 * The main entry point
 */
public class App {

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

    public static void main(final String[] args) throws IOException,
            AWTException {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        
        final SystemTray tray = SystemTray.getSystemTray();
        final Image img = ResourceUtils.loadImage("console-controller2.png");
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

        tray.add(DesktopUtils.createTrayIcon(img, tip, menu));

        // start app
        new App().start();
    }

    private void start() {
        // load conf
        final String readedFile = readConfigFile(CFG_FILE);
        final Configuration configuration = configLoader.loadXml(readedFile);

        // get all commands
        final CommandFactory commandFactory = new CommandFactory(inputUtils,
                configuration);

        final List<Controller> pads = InputUtils.getControllers(Type.GAMEPAD);
        final IControllerCallback callback = new SimpleCallback(commandFactory,
                graphicsDevice);
        final ControllerPoller poller = new ControllerPoller(pads, PERIOD,
                callback);
        poller.start();
    }

    private String readConfigFile(final String filePath) {
        BufferedReader in = null;
        String readedFile = "";
        try {
            in = new BufferedReader(new FileReader(filePath));
            String str;
            final StringBuilder builder = new StringBuilder();
            while ((str = in.readLine()) != null) {
                builder.append(str);
            }

            readedFile = builder.toString();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return readedFile;
    }
}