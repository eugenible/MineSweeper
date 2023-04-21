package org.eugenible.model.game;

public enum Complexity {
    EASY(9, 10),
    MEDIUM(16, 40),
    HARD(24, 99);

    private final int size;
    private final int bombs;

    Complexity(int size, int bombs) {
        this.size = size;
        this.bombs = bombs;
    }

    public int getSize() {
        return size;
    }

    public int getBombs() {
        return bombs;
    }
}
