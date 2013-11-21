package com.xafero.strangectrl.input;

import java.awt.Robot;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;

import com.xafero.superloader.NativeLoader;

public class InputUtils {

	public static List<Controller> getControllers(Type... types) {
		NativeLoader.setupNative("jinput-platform-", "-natives-");
		ControllerEnvironment env = ControllerEnvironment
				.getDefaultEnvironment();
		List<Controller> controllers = new LinkedList<Controller>();
		List<Type> typeList = Arrays.asList(types);
		for (Controller ctrl : env.getControllers())
			if (typeList.contains(ctrl.getType()))
				controllers.add(ctrl);
		return controllers;
	}

	public static void pressKeyCombo(Robot robot, int... codes) {
		Deque<Integer> keyCodes = new ArrayDeque<Integer>();
		for (int code : codes) {
			robot.keyPress(code);
			keyCodes.push(code);
		}
		for (int key : keyCodes)
			robot.keyRelease(key);
	}
}