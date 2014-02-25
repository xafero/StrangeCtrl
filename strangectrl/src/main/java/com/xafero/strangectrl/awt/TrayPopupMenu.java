package com.xafero.strangectrl.awt;

import java.awt.Desktop;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class TrayPopupMenu extends PopupMenu {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(TrayPopupMenu.class);
    private static final long serialVersionUID = 687014251795615363L;
    private static final String HELP_URL = "https://github.com/magx2/StrangeCtrl/wiki";
    private final ControllersRefreshListener refreshListener;
    private final ExitListener exitListener;

    public TrayPopupMenu(final ControllersRefreshListener refreshListener,
            final ExitListener exitListener) {
        super("Strange Control menu");
        this.refreshListener = refreshListener;
        this.exitListener = exitListener;
        initGui();
    }

    private void initGui() {

        // refresh
        initHelp();

        // refresh
        initRefresh();

        // exit
        initExit();
    }

    private void initExit() {
        final ActionListener exitListener = new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                menuExit();
            }
        };
        final MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(exitListener);
        add(exitItem);
    }

    private void initRefresh() {
        // TODO: later
        // final ActionListener refreshListener = new ActionListener() {
        //
        // @Override
        // public void actionPerformed(final ActionEvent e) {
        // menuRefresh();
        // }
        // };
        // final MenuItem refreshItem = new MenuItem("Refresh Controllers");
        // refreshItem.addActionListener(refreshListener);
        // add(refreshItem);
    }

    private void initHelp() {
        final ActionListener helpListener = new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                menuHelp();
            }
        };
        final MenuItem helpItem = new MenuItem("Help");
        helpItem.addActionListener(helpListener);
        add(helpItem);
    }

    private void menuExit() {
        exitListener.exit();
    }

    private void menuRefresh() {
        refreshListener.refreshControllers();
    }

    private void menuHelp() {
        try {
            Desktop.getDesktop().browse(new URL(HELP_URL).toURI());
        } catch (final MalformedURLException e) {
            logger.warn("This error should never happen!", e);
        } catch (final IOException e) {
            logger.error("Cannot open help link!", e);
        } catch (final URISyntaxException e) {
            logger.warn("This error should never happen!", e);
        }
    }
}
