package com.xafero.strangectrl.input;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;

public class ControllersRefresher {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(ControllersRefresher.class);
	private static final long MAX_TIMES_DELAY = 1000;
	private final InputUtils inputUtils;
	private final AtomicLong timesTried = new AtomicLong(0);

	public ControllersRefresher(final InputUtils inputUtils) {
		this.inputUtils = checkNotNull(inputUtils);
	}

	public Set<Controller> getController() {
		if (timesTried.incrementAndGet() >= MAX_TIMES_DELAY) {
			logger.info("Getting new controllers from InputUtils");

			timesTried.set(0);
			return inputUtils.getControllers(Type.GAMEPAD);
		} else {
			return new HashSet<>();
		}

	}
}
