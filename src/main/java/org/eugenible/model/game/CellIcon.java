package org.eugenible.model.game;

import java.util.HashMap;
import java.util.Map;

public enum CellIcon {

    UNKNOWN,
    FLAGGED,
    BOMB_SHOW,
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8);

    private static final Map<Integer, CellIcon> bombCountToIcon = new HashMap<>();

    static {
        for (CellIcon icon : CellIcon.values()) {
            bombCountToIcon.put(icon.bombCount, icon);
        }
    }

    private int bombCount;

    CellIcon(int bombCount) {
        this.bombCount = bombCount;
    }

    CellIcon() {
    }

    public static CellIcon getIconForNearBombCount(int bombsNear) {
        return bombCountToIcon.get(bombsNear);
    }
}