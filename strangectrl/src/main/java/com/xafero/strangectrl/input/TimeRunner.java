package com.xafero.strangectrl.input;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.TimerTask;

public class TimeRunner {
    private final long period;
    private final TimerTask timerTask;
    private volatile boolean run = false;

    public TimeRunner(final long period, final TimerTask timerTask) {
        checkArgument(period > 0);
        this.period = period;
        this.timerTask = checkNotNull(timerTask);
    }

    public void start() {
        checkState(!run);
        run = true;

        while (run) {
            final long startNano = System.currentTimeMillis();

            timerTask.run();

            final long endNano = System.currentTimeMillis();
            final long timeGap = endNano - startNano;

            // waiting
            final long timeToSleep = period - timeGap;
            if (timeToSleep >= 0) {
                try {
                    Thread.sleep(timeToSleep);
                } catch (final InterruptedException e) {
                    // ignore it
                }
            }
        }
    }

    public void stop() {
        checkState(run);

        run = false;
    }
}
