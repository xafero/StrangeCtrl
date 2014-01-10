package com.xafero.strangectrl.input;

import java.awt.Point;
import java.awt.Robot;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;
import pl.grzeslowski.strangectrl.config.Key;

import com.xafero.strangectrl.cmd.ConfigUtils;
import com.xafero.superloader.NativeLoader;

public class InputUtils {

    private static final String prefix = "VK_";
    private final Robot robot;
    private final Map<String, Integer> keyMap;

    public static enum MouseButton {
        LEFT(1), RIGHT(2), CENTER(3);

        private final int buttonMask;

        private MouseButton(final int buttonMask) {
            this.buttonMask = buttonMask;
        }

        public int getButtonMask() {
            return buttonMask;
        }

    }

    public InputUtils(final Robot robot) {
        this.robot = robot;
        keyMap = ConfigUtils.buildKeyMap(prefix);
    }

    public static List<Controller> getControllers(final Type... types) {
        NativeLoader.setupNative("jinput-platform-", "-natives-");
        final ControllerEnvironment env = ControllerEnvironment
                .getDefaultEnvironment();
        final List<Controller> controllers = new LinkedList<Controller>();
        final List<Type> typeList = Arrays.asList(types);
        for (final Controller ctrl : env.getControllers()) {
            if (typeList.contains(ctrl.getType())) {
                controllers.add(ctrl);
            }
        }
        return controllers;
    }

    @Deprecated
    public static void pressKeyCombo(final Robot robot, final int... codes) {
        final Deque<Integer> keyCodes = new ArrayDeque<Integer>();
        for (final int code : codes) {
            robot.keyPress(code);
            keyCodes.push(code);
        }
        for (final int key : keyCodes) {
            robot.keyRelease(key);
        }
    }

    public void pressKey(final List<Key> keys) {
        for (final Key key : keys) {
            robot.keyPress(getCode(key));
        }
    }

    public void pressKey(final Key... keys) {
        pressKey(Arrays.asList(keys));
    }

    private int getCode(final Key key) {
        return keyMap.get(key.getKey().toLowerCase(Locale.US));
    }

    public void releaseKey(final List<Key> keys) {
        for (final Key key : keys) {
            robot.keyRelease(getCode(key));
        }
    }

    public void releaseKey(final Key... keys) {
        releaseKey(Arrays.asList(keys));
    }

    public void pressKeyCombo(final List<Key> keys) {
        final Deque<Key> keyCodes = new ArrayDeque<>();
        for (final Key key : keys) {
            robot.keyPress(getCode(key));
            keyCodes.push(key);
        }
        for (final Key key : keyCodes) {
            robot.keyRelease(getCode(key));
        }
    }

    public void pressKeyCombo(final Key... keys) {
        pressKeyCombo(Arrays.asList(keys));
    }

    public void moveMouse(final Point point) {
        robot.mouseMove(point.x, point.y);
    }

    public void mousePress(final MouseButton button) {
        robot.mousePress(button.buttonMask);
    }

    public void mousePressLeft() {
        mousePress(MouseButton.LEFT);
    }

    public void mousePressRight() {
        mousePress(MouseButton.RIGHT);
    }

    public void mousePressCenter() {
        mousePress(MouseButton.CENTER);
    }

}