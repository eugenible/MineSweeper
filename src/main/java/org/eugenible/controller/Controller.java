package org.eugenible.controller;

import org.eugenible.model.game.Complexity;
import org.eugenible.model.interaction.GameManager;
import org.eugenible.model.interaction.MouseButton;
import org.eugenible.view.elements.ButtonType;
import org.eugenible.view.elements.GameType;
import org.eugenible.view.viewListeners.CellEventListener;
import org.eugenible.view.viewListeners.GameTypeListener;
import org.eugenible.view.viewListeners.NewGameListener;
import org.eugenible.view.viewListeners.RecordNameListener;

public class Controller implements CellEventListener, GameTypeListener, RecordNameListener, NewGameListener {
    private GameManager manager;  // Model

    public Controller(GameManager manager) {
        this.manager = manager;
    }

    @Override
    public void onMouseClick(int x, int y, ButtonType buttonType) {
        switch (buttonType) {
            case LEFT_BUTTON -> manager.mouseClick(MouseButton.LEFT, x, y);
            case MIDDLE_BUTTON -> manager.mouseClick(MouseButton.MIDDLE, x, y);
            case RIGHT_BUTTON -> manager.mouseClick(MouseButton.RIGHT, x, y);
        }
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

    @Override
    public void onRecordNameEntered(String name) {
        manager.updateRecordList(name);
    }

    @Override
    public void onNewGameClicked() {
        manager.startNewGame();
    }
}
