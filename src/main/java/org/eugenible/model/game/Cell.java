package org.eugenible.model.game;


import org.eugenible.model.game.states.CellState;
import org.eugenible.model.game.states.DiscoveredSafeState;
import org.eugenible.model.game.states.FlaggedState;
import org.eugenible.model.game.states.UnknownState;

public class Cell {
    private final Coordinate coordinate;
    private int bombsNearCount;
    private boolean isBomb;
    private final Field field;
    private CellState state;
    private CellIcon icon;

    Cell(Field field, Coordinate coordinate, boolean isBomb) {
        this.coordinate = coordinate;
        this.field = field;
        this.icon = CellIcon.UNKNOWN;
        this.state = new UnknownState(this);
        this.isBomb = isBomb;
    }

    public boolean tryRevealSafeWithoutClick() {
        if (!isBomb) {
            if (state instanceof FlaggedState) field.incrementAssumedMinesRemainder();
            icon = CellIcon.getIconForNearBombCount(bombsNearCount);
            state = new DiscoveredSafeState(this);
            return true;
        }
        return false;
    }

    public void leftMouseClick() {
        state.leftMouseClick();
    }

    public void middleMouseClick() {
        state.middleMouseClick();
    }

    public int countFlagsAround() {
        return field.countFlagsAround(coordinate.getX(), coordinate.getY());
    }

    public void leftClickAllSurrounding() {
        this.field.leftClickAllSurrounding(coordinate.getX(), coordinate.getY());
    }

    public void rightMouseClick() {
        this.state.rightMouseClick();
    }

    public Field getField() {
        return field;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }

    public int getBombsNearCount() {
        return bombsNearCount;
    }

    public void setBombsNearCount(int bombsNearCount) {
        this.bombsNearCount = bombsNearCount;
    }

    public CellIcon getIcon() {
        return icon;
    }

    public void setIcon(CellIcon icon) {
        this.icon = icon;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
