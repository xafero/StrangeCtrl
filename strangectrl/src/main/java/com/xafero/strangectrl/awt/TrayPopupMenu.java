package com.xafero.strangectrl.awt;

import java.awt.HeadlessException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrayPopupMenu extends PopupMenu {

	private static final long serialVersionUID = 687014251795615363L;

	public TrayPopupMenu() throws HeadlessException {
		super("StrangeController menu");
		initGui();
	}

	private void initGui() {

		// exit
		final ActionListener menuItemListener = new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				menuExit();
			}
		};
		final MenuItem exitItem = new MenuItem("Exit");
		exitItem.addActionListener(menuItemListener);
		add(exitItem);
	}

	protected void menuExit() {
		System.exit(0);
	}

}
