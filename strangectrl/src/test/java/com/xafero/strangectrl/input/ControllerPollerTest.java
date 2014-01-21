package com.xafero.strangectrl.input;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import net.java.games.input.Controller;
import net.java.games.input.EventQueue;

import org.junit.Test;

public class ControllerPollerTest {

	@Test
	public void controller_is_no_longer_valid() throws Exception {

		// given
		final Controller controller = mock(Controller.class);
		when(controller.poll()).thenReturn(false);

		final IControllerCallback callback = mock(IControllerCallback.class);
		final ControllersRefresher refresher = mock(ControllersRefresher.class);

		final ControllerPoller poller = spy(new ControllerPoller(refresher,
				controller, callback));

		// when
		poller.run();

		// then
		verify(poller).cancel();
		verify(callback).controllerRemoved();
		verify(refresher).controllerNotAvailable(controller);
	}

	@Test
	public void controller_valid() throws Exception {

		// given
		final Controller controller = mock(Controller.class);
		when(controller.poll()).thenReturn(true);
		when(controller.getEventQueue()).thenReturn(new EventQueue(0));

		final IControllerCallback callback = mock(IControllerCallback.class);
		final ControllersRefresher refresher = mock(ControllersRefresher.class);

		final ControllerPoller poller = spy(new ControllerPoller(refresher,
				controller, callback));

		// when
		poller.run();

		// then
		verify(controller).poll();
		verify(controller).getEventQueue();
	}

	@Test(expected = IllegalArgumentException.class)
	public void do_not_run_when_after_canceling_timer_task() throws Exception {

		// given
		final Controller controller = mock(Controller.class);
		when(controller.poll()).thenReturn(false);

		final IControllerCallback callback = mock(IControllerCallback.class);
		final ControllersRefresher refresher = mock(ControllersRefresher.class);

		final ControllerPoller poller = spy(new ControllerPoller(refresher,
				controller, callback));

		// when
		poller.run();
		poller.run();
	}
}
