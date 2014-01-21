package com.xafero.strangectrl.input;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;

public class ControllersRefresher {
	private final InputUtils inputUtils;

	public ControllersRefresher(final InputUtils inputUtils) {
		this.inputUtils = checkNotNull(inputUtils);
	}

	public Set<Controller> getController() {
		return inputUtils.getControllers(Type.GAMEPAD);
	}
}
