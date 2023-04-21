package org.eugenible.model.modelListeners;

@FunctionalInterface
public interface TimerListener {
    void onTimerTicked(int seconds);
}
