package org.eugenible.model.game.states;

import org.eugenible.model.game.Cell;

public class DiscoveredSafeState extends CellState {

    public DiscoveredSafeState(Cell cell) {
        super(cell);
    }

    @Override
    public void leftMouseClick() {
        // No-op
    }

    @Override
    public void middleMouseClick() {
        if (cell.countFlagsAround() == cell.getBombsNearCount()) {
            cell.leftClickAllSurrounding();
        }
    }

    @Override
    public void rightMouseClick() {
        // No-op
    }
}
