package org.eugenible.model.game.states;

import org.eugenible.model.game.Cell;

public abstract class CellState {
    protected Cell cell;

    public CellState(Cell cell) {
        this.cell = cell;
    }

    public abstract void leftMouseClick();

    public abstract void middleMouseClick();

    public abstract void rightMouseClick();
}
