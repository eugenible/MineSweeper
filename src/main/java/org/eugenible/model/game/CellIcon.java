package org.eugenible.model.game;

public enum CellIcon {
    UNKNOWN,
    FLAGGED,
    BOMB_SHOW,
    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT;

    public static CellIcon getIconForNearBombCount(int bombsNear) {
        return switch (bombsNear) {
            case 0 -> ZERO;
            case 1 -> ONE;
            case 2 -> TWO;
            case 3 -> THREE;
            case 4 -> FOUR;
            case 5 -> FIVE;
            case 6 -> SIX;
            case 7 -> SEVEN;
            case 8 -> EIGHT;
            default -> null;
        };
    }
}