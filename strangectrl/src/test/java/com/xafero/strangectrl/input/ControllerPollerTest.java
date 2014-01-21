package com.xafero.strangectrl.input;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import net.java.games.input.Controller;

import org.junit.Test;

public class ControllerPollerTest {

	@Test
	public void controller_is_no_longer_valid() throws Exception {

		// given
		final Controller controller = mock(Controller.class);
		when(controller.poll()).thenReturn(false);

		final long period = 100;
		final IControllerCallback callback = mock(IControllerCallback.class);
		final ControllersRefresher refresher = mock(ControllersRefresher.class);

		final ControllerPoller poller = spy(new ControllerPoller(refresher,
				controller,
				period, callback));

		// when
		poller.start();

		// then
		verify(poller).stop();
		verify(callback).controllerRemoved();
		verify(refresher).controllerNotAvaible(controller);
	}
}
