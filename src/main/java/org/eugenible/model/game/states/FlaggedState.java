package org.eugenible.model.game.states;

import org.eugenible.model.game.Cell;
import org.eugenible.model.game.CellIcon;

public class FlaggedState extends CellState {

    public FlaggedState(Cell cell) {
        super(cell);
    }

    @Override
    public void leftMouseClick() {
        // No-op
    }

    @Override
    public void middleMouseClick() {
        // No-op
    }

    @Override
    public void rightMouseClick() {
        cell.setIcon(CellIcon.UNKNOWN);
        cell.setState(new UnknownState(cell));
        cell.getField().incrementAssumedMinesRemainder();
    }
}
