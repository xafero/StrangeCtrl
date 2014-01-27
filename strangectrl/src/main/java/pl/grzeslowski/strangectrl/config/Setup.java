package pl.grzeslowski.strangectrl.config;

import static com.google.common.base.Objects.equal;

import com.google.common.base.Objects;

public class Setup {
	private static final int MAX_MOUSE_MOVE = 15;
	private static final int TIME_REFRESHER = 10;
	private static final int CHECK_NEW_CONTROLLERS_DELAY = 10;
	private static final int SCROLL_LINES = 1;
	private static final Setup DEFAULT = new Setup(MAX_MOUSE_MOVE,
			TIME_REFRESHER, CHECK_NEW_CONTROLLERS_DELAY, SCROLL_LINES);

	private int maxMouseMove = MAX_MOUSE_MOVE;
	private int timeRefresher = TIME_REFRESHER;
	private int checkNewControllersDelay = CHECK_NEW_CONTROLLERS_DELAY;
	private int scrollLines = SCROLL_LINES;

	public static Setup getDefaultSetup() {
		return DEFAULT;
	}

	private Setup() {
		// for XStreamConfigLoader
	}

	public Setup(final int maxMouseMove, final int timeRefresher,
			final int checkNewControllersDelay, final int scrollLines) {
		this.maxMouseMove = maxMouseMove;
		this.timeRefresher = timeRefresher;
		this.checkNewControllersDelay = checkNewControllersDelay;
		this.scrollLines = scrollLines;
	}

	public int getMaxMouseMove() {
		return maxMouseMove;
	}

	public int getTimeRefresher() {
		return timeRefresher;
	}

	public int getCheckNewControllersDelay() {
		return checkNewControllersDelay;
	}

	public int getScrollLines() {
		return scrollLines;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(maxMouseMove, timeRefresher,
				checkNewControllersDelay, scrollLines);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Setup) {
			final Setup setup = (Setup) obj;

			return equal(maxMouseMove, setup.maxMouseMove)
					&& equal(timeRefresher, setup.timeRefresher)
					&& equal(checkNewControllersDelay,
							setup.checkNewControllersDelay)
							&& equal(scrollLines, setup.scrollLines);
		} else {
			return false;
		}
	}
}
