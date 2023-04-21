package org.eugenible.model.game;


import org.eugenible.model.game.states.DiscoveredSafeState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Field {
    private final Cell[][] cells;
    private final int rows;
    private final int cols;
    private final int mineNumber;
    List<Coordinate> mineCoordinates;
    private int safeCellRemainder;
    private int assumedMinesRemainder;
    private boolean bombExploded;

    public Field(int rows, int cols, int mineNumber) {
        this.mineCoordinates = new ArrayList<>(mineNumber);
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[rows][cols];
        setCellsToDefault();
        this.mineNumber = mineNumber;
        this.safeCellRemainder = rows * cols - mineNumber;
        this.assumedMinesRemainder = mineNumber;
        this.bombExploded = false;
    }

    public void generateMineCoordinates(int xClick, int yClick) {
        Random random = new Random();

        for (int i = 0; i < mineNumber; ) {
            int x = random.nextInt(rows);
            int y = random.nextInt(cols);
            Coordinate coordinate = new Coordinate(x, y);

            if (x == xClick && y == yClick || mineCoordinates.contains(coordinate)) continue;

            mineCoordinates.add(coordinate);
            i++;
        }
    }

    public int countFlagsAround(int x, int y) {
        int flagCount = 0;
        for (int row = x - 1; row <= x + 1; row++) {
            if (row < 0 || row >= rows) continue;
            for (int col = y - 1; col <= y + 1; col++) {
                if (col < 0 || col >= cols || (x == row && y == col)) continue;
                if (cells[row][col].getIcon() == CellIcon.FLAGGED) flagCount++;
            }
        }

        return flagCount;
    }

    public void leftClickAllSurrounding(int x, int y) {
        for (int row = x - 1; row <= x + 1; row++) {
            if (row < 0 || row >= rows) continue;
            for (int col = y - 1; col <= y + 1; col++) {
                if (col < 0 || col >= cols || (x == row && y == col)) continue;

                if (cells[row][col].getIcon().equals(CellIcon.UNKNOWN)) {
                    leftMouseClick(row, col);
                }
                if (bombExploded) return;
            }
        }
    }

    // 8-directional flood filling algorithm
    public void floodFill(int x, int y) {
        if (areCoordinatesOutOfField(x, y) || wasCellVisited(x, y)) return;
        if (cells[x][y].tryRevealSafeWithoutClick()) decrementSafeCellsRemainder();
        if (cells[x][y].getBombsNearCount() != 0) return;

        for (int row = x - 1; row <= x + 1; row++) {
            if (row < 0 || row >= rows) continue;
            for (int col = y - 1; col <= y + 1; col++) {
                if (col < 0 || col >= cols || (row == x && col == y)) continue;
                floodFill(row, col);
            }
        }
    }

    private boolean wasCellVisited(int x, int y) {
        return cells[x][y].getState() instanceof DiscoveredSafeState;
    }

    private boolean areCoordinatesOutOfField(int x, int y) {
        return x < 0 || x >= rows || y < 0 || y >= cols;
    }

    public void markAllMinesWithIcon(CellIcon icon) {
        for (Coordinate c : mineCoordinates) {
            this.cells[c.getX()][c.getY()].setIcon(icon);
        }
    }

    void leftMouseClick(int x, int y) {
        this.cells[x][y].leftMouseClick();
    }

    void middleMouseClick(int x, int y) {
        cells[x][y].middleMouseClick();
        if (cells[x][y].getIcon().equals(CellIcon.ZERO)) {
            floodFill(x, y);
        }
    }

    void rightMouseClick(int x, int y) {
        this.cells[x][y].rightMouseClick();
    }

    public void placeMines() {
        int noBombCountNeeded = -1;
        for (Coordinate c : mineCoordinates) {
            Cell bomb = cells[c.getX()][c.getY()];
            bomb.setBomb(true);
            bomb.setBombsNearCount(noBombCountNeeded);
        }
    }


    public void setCellsToDefault() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                cells[r][c] = new Cell(this, new Coordinate(r, c), false);
            }
        }
    }


    private int calculateBombsNearCoordinate(int x, int y) {
        int bombCount = 0;
        for (int row = x - 1; row <= x + 1; row++) {
            if (row < 0 || row >= rows) continue;
            for (int col = y - 1; col <= y + 1; col++) {
                if (col < 0 || col >= cols || (x == row && y == col)) continue;
                if (cells[row][col].isBomb()) bombCount++;
            }
        }
        return bombCount;
    }

    public void countBombsAroundSafeCells() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = cells[r][c];
                if (cell.isBomb()) continue;
                cell.setBombsNearCount(calculateBombsNearCoordinate(r, c));
            }
        }
    }

    public int getSafeCellRemainder() {
        return safeCellRemainder;
    }

    public boolean isBombExploded() {
        return bombExploded;
    }

    public void setBombExploded(boolean bombExploded) {
        this.bombExploded = bombExploded;
    }

    public int getAssumedMinesRemainder() {
        return assumedMinesRemainder;
    }

    public void setAssumedMinesRemainder(int assumedMinesRemainder) {
        this.assumedMinesRemainder = assumedMinesRemainder;
    }

    public void decrementSafeCellsRemainder() {
        safeCellRemainder--;
    }

    public void incrementAssumedMinesRemainder() {
        assumedMinesRemainder++;
    }

    public void decrementAssumedMinesRemainder() {
        assumedMinesRemainder--;
    }

    public Cell[][] getCells() {
        return cells;
    }
}
