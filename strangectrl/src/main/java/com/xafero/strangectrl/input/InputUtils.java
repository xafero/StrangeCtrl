package com.xafero.strangectrl.input;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;
import pl.grzeslowski.strangectrl.config.Key;

import com.xafero.superloader.NativeLoader;

public class InputUtils {

    private final Robot robot;

    public InputUtils(final Robot robot) {
        this.robot = robot;
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
        return KeyEvent.VK_Q;
    }

    public void releaseKey(final List<Key> keys) {
        for (final Key key : keys) {
            robot.keyRelease(getCode(key));
        }
    }
    
    public void releaseKey(final Key... keys) {
        releaseKey(Arrays.asList(keys));
    }
}