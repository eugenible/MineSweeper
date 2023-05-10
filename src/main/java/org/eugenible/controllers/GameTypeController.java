package org.eugenible.controllers;

import org.eugenible.model.game.Complexity;
import org.eugenible.model.interaction.GameManager;
import org.eugenible.view.elements.GameType;
import org.eugenible.view.viewListeners.GameTypeListener;

public class GameTypeController implements GameTypeListener {

    private final GameManager manager;

    public GameTypeController(GameManager manager) {
        this.manager = manager;
    }

    @Override
    public void onGameTypeChanged(GameType gameType) {
        switch (gameType) {
            case NOVICE -> manager.setNextGameComplexity(Complexity.EASY);
            case MEDIUM -> manager.setNextGameComplexity(Complexity.MEDIUM);
            case EXPERT -> manager.setNextGameComplexity(Complexity.HARD);
        }
        manager.startNewGame();
    }
}
