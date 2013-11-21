package com.xafero.strangectrl.cmd;

import java.awt.GraphicsDevice;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Arrays;
import java.util.Map;

import com.xafero.strangectrl.awt.DesktopUtils;
import com.xafero.strangectrl.input.InputUtils;

public class Commands {

	public static class MouseMoveCmd extends AbstractCmd implements ICommand {
		private final double factor;
		private final char mode;

		public MouseMoveCmd(String rawArgs) {
			super(rawArgs);
			factor = Double.parseDouble(args[0]);
			mode = args[1].toUpperCase().charAt(0);
		}

		@Override
		public void execute(Robot rbt, GraphicsDevice dev, float value) {
			double newValue = value * factor;
			newValue = Math.ceil(newValue);
			Point pos = DesktopUtils.getMousePos(dev);
			int newX = pos.x;
			int newY = pos.y;
			if (mode == 'X')
				newX = (int) (pos.x + newValue);
			if (mode == 'Y')
				newY = (int) (pos.y + newValue);
			rbt.mouseMove(newX, newY);
		}

		@Override
		public String toString() {
			return "MouseMoveCmd [factor=" + factor + ", mode=" + mode + "]";
		}
	}

	public static class MouseClickCmd extends AbstractCmd implements ICommand {
		private final int button;

		public MouseClickCmd(String rawArgs) {
			super(rawArgs);
			button = Integer.parseInt(args[0]);
		}

		@Override
		public void execute(Robot rbt, GraphicsDevice dev, float value) {
			if (value != 1)
				return;
			int btnMask = InputEvent.getMaskForButton(button);
			rbt.mousePress(btnMask);
			rbt.mouseRelease(btnMask);
		}

		@Override
		public String toString() {
			return "MouseClickCmd [button=" + button + "]";
		}
	}

	public static class KeyComboCmd extends AbstractCmd implements ICommand {
		private static final String PREFIX = "VK_";
		private static final Map<String, Integer> keyMap = ConfigUtils
				.buildKeyMap(PREFIX);

		private int[] keys;

		public KeyComboCmd(String rawArgs) {
			super(rawArgs);
			keys = new int[args.length];
			for (int i = 0; i < keys.length; i++) {
				String arg = args[i].toLowerCase();
				keys[i] = keyMap.get(arg);
			}
		}

		@Override
		public void execute(Robot rbt, GraphicsDevice dev, float value) {
			if (value != 1)
				return;
			InputUtils.pressKeyCombo(rbt, keys);
		}

		@Override
		public String toString() {
			return "KeyComboCmd [keys=" + Arrays.toString(keys) + ", args="
					+ Arrays.toString(args) + "]";
		}
	}
}