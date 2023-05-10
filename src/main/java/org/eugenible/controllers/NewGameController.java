package org.eugenible.controllers;

import org.eugenible.model.interaction.GameManager;
import org.eugenible.view.viewListeners.NewGameListener;

public class NewGameController implements NewGameListener {
    private final GameManager manager;

    public NewGameController(GameManager manager) {
        this.manager = manager;
    }

    @Override
    public void onNewGameClicked() {
        manager.startNewGame();
    }
}
