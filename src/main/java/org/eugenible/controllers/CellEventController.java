package org.eugenible.controllers;

import lombok.Getter;
import org.eugenible.model.interaction.GameManager;
import org.eugenible.view.elements.ButtonType;
import org.eugenible.view.viewListeners.CellEventListener;

@Getter
public class CellEventController implements CellEventListener {

    private final GameManager manager;

    public CellEventController(GameManager manager) {
        this.manager = manager;
    }

    @Override
    public void onMouseClick(int x, int y, ButtonType buttonType) {
        manager.mouseClick(buttonType, x, y);
    }
}
