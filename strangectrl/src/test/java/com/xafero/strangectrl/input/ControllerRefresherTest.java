package com.xafero.strangectrl.input;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import net.java.games.input.Controller.Type;

import org.junit.Test;
import org.mockito.Mockito;

public class ControllerRefresherTest {
	@Test
	public void refreshControllersAfterXInvocations() throws Exception {

		// given
		final InputUtils inputUtils = mock(InputUtils.class);
		final ControllersRefresher refresher = new ControllersRefresher(
				inputUtils, 3, 1000);

		// when
		refresher.getController();
		refresher.getController();
		refresher.getController();

		// then
		verify(inputUtils).getControllers(Mockito.any(Type[].class));
	}

	@Test
	public void refreshControllersForFirstTime() throws Exception {

		// given
		final InputUtils inputUtils = mock(InputUtils.class);
		final ControllersRefresher refresher = new ControllersRefresher(
				inputUtils, 100, 1);

		// when
		refresher.getController();

		// then
		verify(inputUtils).getControllers(Mockito.any(Type[].class));
	}

	@Test
	public void refreshControllersAfterWaitingTime() throws Exception {

		// given
		final InputUtils inputUtils = mock(InputUtils.class);
		final int timeInSecs = 1;
		final ControllersRefresher refresher = new ControllersRefresher(
				inputUtils, timeInSecs, 1);

		// when
		refresher.getController();
		Thread.sleep(timeInSecs * 2 * 1000);
		refresher.getController();
		refresher.getController();

		// then
		verify(inputUtils, times(2)).getControllers(Mockito.any(Type[].class));
	}
}
