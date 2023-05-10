package org.eugenible.model.game.states;

import org.eugenible.model.game.Cell;
import org.eugenible.model.game.CellIcon;
import org.eugenible.model.game.Field;

public enum CellState {

    COVERED_STATE() {
        @Override
        public void leftMouseClick(Cell cell, Field field) {
            if (cell.isBomb()) {
                cell.setIcon(CellIcon.BOMB_SHOW);
                field.setBombExploded(true);
            } else if (cell.getBombsNearCount() == 0) {
                field.floodFill(cell.getCoordinate().getX(), cell.getCoordinate().getY());
            } else {
                cell.setState(REVEALED_STATE);
                cell.setIcon(CellIcon.getIconForNearBombCount(cell.getBombsNearCount()));
                field.decrementSafeCellsRemainder();
            }
        }

        @Override
        public void middleMouseClick(Cell cell, Field field) {
            // No-op
        }

        @Override
        public void rightMouseClick(Cell cell, Field field) {
            cell.setIcon(CellIcon.FLAGGED);
            cell.setState(FLAGGED_STATE);
            field.decrementAssumedMinesRemainder();
        }
    },

    FLAGGED_STATE() {
        @Override
        public void leftMouseClick(Cell cell, Field field) {
            // No-op
        }

        @Override
        public void middleMouseClick(Cell cell, Field field) {
            // No-op
        }

        @Override
        public void rightMouseClick(Cell cell, Field field) {
            cell.setState(COVERED_STATE);
            cell.setIcon(CellIcon.UNKNOWN);
            field.incrementAssumedMinesRemainder();
        }
    },

    REVEALED_STATE() {
        @Override
        public void leftMouseClick(Cell cell, Field field) {
            // No-op
        }

        @Override
        public void middleMouseClick(Cell cell, Field field) {
            if (field.countFlagsAround(cell.getCoordinate()) == cell.getBombsNearCount()) {
                field.leftClickAllSurrounding(cell.getCoordinate());
            }
        }

        @Override
        public void rightMouseClick(Cell cell, Field field) {
            // No-op
        }
    };

    public abstract void leftMouseClick(Cell cell, Field field);

    public abstract void middleMouseClick(Cell cell, Field field);

    public abstract void rightMouseClick(Cell cell, Field field);
}
