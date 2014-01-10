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

		public MouseMoveCmd(final String rawArgs) {
			super(rawArgs);
			factor = Double.parseDouble(args[0]);
			mode = args[1].toUpperCase().charAt(0);
		}

		public void execute(final Robot rbt, final GraphicsDevice dev, final float value) {
			double newValue = value * factor;
			newValue = Math.ceil(newValue);
			final Point pos = DesktopUtils.getMousePos(dev);
			int newX = pos.x;
			int newY = pos.y;
			if (mode == 'X') {
                newX = (int) (pos.x + newValue);
            }
			if (mode == 'Y') {
                newY = (int) (pos.y + newValue);
            }
			rbt.mouseMove(newX, newY);
		}

		@Override
		public String toString() {
			return "MouseMoveCmd [factor=" + factor + ", mode=" + mode + "]";
		}

        @Override
        public void execute(final GraphicsDevice graphicsDevice, final double value) {
            // TODO Auto-generated method stub
            
        }
	}

	public static class MouseClickCmd extends AbstractCmd implements ICommand {
		private final int button;

		public MouseClickCmd(final String rawArgs) {
			super(rawArgs);
			button = Integer.parseInt(args[0]);
		}

		public void execute(final Robot rbt, final GraphicsDevice dev, final float value) {
			if (value != 1) {
                return;
            }
			final int btnMask = InputEvent.getMaskForButton(button);
			rbt.mousePress(btnMask);
			rbt.mouseRelease(btnMask);
		}

		@Override
		public String toString() {
			return "MouseClickCmd [button=" + button + "]";
		}

        @Override
        public void execute(final GraphicsDevice graphicsDevice, final double value) {
            // TODO Auto-generated method stub
            
        }
	}

	public static class KeyComboCmd extends AbstractCmd implements ICommand {
		private static final String PREFIX = "VK_";
		private static final Map<String, Integer> keyMap = ConfigUtils
				.buildKeyMap(PREFIX);

		private final int[] keys;

		public KeyComboCmd(final String rawArgs) {
			super(rawArgs);
			keys = new int[args.length];
			for (int i = 0; i < keys.length; i++) {
				final String arg = args[i].toLowerCase();
				keys[i] = keyMap.get(arg);
			}
		}

		public void execute(final Robot rbt, final GraphicsDevice dev, final float value) {
			if (value != 1) {
                return;
            }
			InputUtils.pressKeyCombo(rbt, keys);
		}

		@Override
		public String toString() {
			return "KeyComboCmd [keys=" + Arrays.toString(keys) + ", args="
					+ Arrays.toString(args) + "]";
		}

        @Override
        public void execute(final GraphicsDevice graphicsDevice, final double value) {
            // TODO Auto-generated method stub
            
        }
	}
}