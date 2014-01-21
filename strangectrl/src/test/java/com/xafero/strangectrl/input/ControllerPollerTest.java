package com.xafero.strangectrl.input;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import net.java.games.input.Controller;
import net.java.games.input.EventQueue;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ControllerPollerTest {

	@Test
	public void controller_is_no_longer_valid() throws Exception {

		// given
		final Controller controller = mock(Controller.class);
		when(controller.poll()).thenReturn(false);

		final IControllerCallback callback = mock(IControllerCallback.class);

		final int period = 100;
		final ControllerPoller poller = spy(new ControllerPoller(
				Arrays.asList(controller), callback,
				mock(ControllersRefresher.class), period));

		// when
		poller.start();

		// need to sleep to wait for TimerTask execute
		try {
			Thread.sleep(period * 3);
		} catch (final InterruptedException ex) {
		}

		// then
		verify(callback).controllerRemoved();
	}

	@Test
	public void controller_valid_one_time() throws Exception {

		// given
		final Controller controller = mock(Controller.class);
		when(controller.poll()).thenAnswer(new Answer<Boolean>() {

			private boolean first = true;

			@Override
			public Boolean answer(final InvocationOnMock invocation)
					throws Throwable {
				final Boolean isPoll = first;
				first = !first;
				return isPoll;
			}
		});
		when(controller.getEventQueue()).thenReturn(new EventQueue(0));

		final IControllerCallback callback = mock(IControllerCallback.class);

		final long period = 100;
		final ControllerPoller poller = spy(new ControllerPoller(
				Arrays.asList(controller), callback,
				mock(ControllersRefresher.class), period));

		// when
		poller.start();

		// need to sleep to wait for TimerTask execute
		try {
			Thread.sleep(period * 3);
		} catch (final InterruptedException ex) {
		}

		// then
		verify(controller, times(2)).poll();
		verify(controller).getEventQueue();
	}

	@Test
	public void refresh_controllers_when_queue_is_empty() throws Exception {

		// given
		final Controller controller = mock(Controller.class);
		when(controller.poll()).thenReturn(false);

		final IControllerCallback callback = mock(IControllerCallback.class);

		final int period = 100;
		final ControllersRefresher controllersRefresher = mock(ControllersRefresher.class);
		final ControllerPoller poller = spy(new ControllerPoller(
				Arrays.asList(controller), callback,
				controllersRefresher,
				period));

		// when
		poller.start();

		// need to sleep to wait for TimerTask execute
		try {
			Thread.sleep(period * 3);
		} catch (final InterruptedException ex) {
		}

		// then
		verify(callback).controllerRemoved();
		verify(controllersRefresher, atLeast(1)).getController();
	}

	@Test(expected = IllegalStateException.class)
	public void cannot_start_twice() throws Exception {

		// given
		final Controller controller = mock(Controller.class);
		when(controller.poll()).thenReturn(false);

		final IControllerCallback callback = mock(IControllerCallback.class);

		final int period = 100;
		final ControllerPoller poller = spy(new ControllerPoller(
				Arrays.asList(controller), callback,
				mock(ControllersRefresher.class), period));

		// when
		poller.start();
		poller.start();

	}

}
