package org.eugenible.view.elements;

public enum GameType {
    NOVICE,
    MEDIUM,
    EXPERT;

    // Вызывается в методе SettingsWindow и при инициализации MainWindow;
    public static GameType getDefault() {
        return NOVICE;
    }
}
