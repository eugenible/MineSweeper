package org.eugenible.model.game;


import lombok.Getter;
import lombok.Setter;
import org.eugenible.model.game.states.CellState;

@Getter
@Setter
public class Cell {
    private final Coordinate coordinate;
    private int bombsNearCount;
    private boolean isBomb;
    private CellState state;
    private CellIcon icon;

    Cell(Coordinate coordinate, boolean isBomb) {
        this.coordinate = coordinate;
        this.icon = CellIcon.UNKNOWN;
        this.state = CellState.COVERED_STATE;
        this.isBomb = isBomb;
    }

    public boolean tryRevealSafeWithoutClick(Field field) {
        if (!isBomb) {
            if (state == CellState.FLAGGED_STATE) {
                field.incrementAssumedMinesRemainder();
            }
            icon = CellIcon.getIconForNearBombCount(bombsNearCount);
            state = CellState.REVEALED_STATE;
            return true;
        }
        return false;
    }

    public void leftMouseClick(Field field) {
        state.leftMouseClick(this, field);
    }

    public void middleMouseClick(Field field) {
        state.middleMouseClick(this, field);
    }

    public void rightMouseClick(Field field) {
        state.rightMouseClick(this, field);
    }
}
