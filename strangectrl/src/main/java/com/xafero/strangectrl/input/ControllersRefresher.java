package com.xafero.strangectrl.input;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;

public class ControllersRefresher {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(ControllersRefresher.class);
	private final InputUtils inputUtils;
	private final AtomicLong timesTried = new AtomicLong(0);
	private final long maxTimesTried;
	private long lastCheckTimestamp = 0;
	private final long timeToWait;

	public ControllersRefresher(final InputUtils inputUtils,
			final long timeInSecs, final long delayInMillis) {
		this.inputUtils = checkNotNull(inputUtils);
		timeToWait = timeInSecs * 1000;
		maxTimesTried = timeToWait / delayInMillis;
	}

	public Set<Controller> getController() {
		final long timeStamp = new Date().getTime();

		if (lastCheckTimestamp + timeToWait < timeStamp
				|| timesTried.incrementAndGet() >= maxTimesTried) {
			logger.debug("Getting new controllers from InputUtils");

			timesTried.set(0);
			lastCheckTimestamp = timeStamp;

			return inputUtils.getControllers(Type.GAMEPAD);
		} else {
			return new HashSet<>();
		}

	}
}
