package org.eugenible.model.game.states;

import org.eugenible.model.game.Cell;
import org.eugenible.model.game.CellIcon;

public class UnknownState extends CellState {

    public UnknownState(Cell cell) {
        super(cell);
    }

    @Override
    public void leftMouseClick() {
        if (cell.isBomb()) {
            cell.setIcon(CellIcon.BOMB_SHOW);
            cell.getField().setBombExploded(true);
        } else if (cell.getBombsNearCount() == 0) {
            cell.getField().floodFill(cell.getCoordinate().getX(), cell.getCoordinate().getY());
        } else {
            cell.setIcon(CellIcon.getIconForNearBombCount(cell.getBombsNearCount()));
            cell.setState(new DiscoveredSafeState(cell));
            cell.getField().decrementSafeCellsRemainder();
        }
    }

    @Override
    public void middleMouseClick() {
        // No-op
    }

    @Override
    public void rightMouseClick() {
        cell.setIcon(CellIcon.FLAGGED);
        cell.setState(new FlaggedState(cell));
        cell.getField().decrementAssumedMinesRemainder();
    }
}
