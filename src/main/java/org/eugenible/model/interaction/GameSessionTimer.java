package org.eugenible.model.interaction;

import org.eugenible.model.modelListeners.TimerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameSessionTimer {
    private final List<TimerListener> timerListeners;

    private final Timer timer;
    private final TimerTask tickEverySecond;
    private int currentGameDuration;

    public GameSessionTimer(List<TimerListener> timerListeners) {
        this.timerListeners = new ArrayList<>(timerListeners);
        timer = new Timer();
        currentGameDuration = 0;
        tickEverySecond = new TimerTask() {
            @Override
            public void run() {
                notifyTimerListeners();
                incrementDuration();
            }
        };
    }

    private void notifyTimerListeners() {
        for (TimerListener listener : timerListeners) {
            listener.onTimerTicked(currentGameDuration);
        }
    }

    private void incrementDuration() {
        currentGameDuration++;
    }

    public void startTimer() {
        timer.schedule(tickEverySecond, 0, 1000);
    }

    public void stopTimer() {
        timer.cancel();
    }

    public int getCurrentGameDuration() {
        return currentGameDuration;
    }
}
