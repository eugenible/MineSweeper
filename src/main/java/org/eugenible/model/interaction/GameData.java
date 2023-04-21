package org.eugenible.model.interaction;


import org.eugenible.model.game.Cell;
import org.eugenible.model.game.CellIcon;
import org.eugenible.model.game.Complexity;
import org.eugenible.model.game.GameSession;

// Для передачи в View
public class GameData {

    private CellIcon[][] icons;
    private Complexity complexity;
    private int assumedMineRemainder;
    private GameStatus gameStatus;
    private boolean isRecord;

    public static GameData getDataFromSession(GameSession gameSession) {
        GameData gameData = new GameData();
        gameData.complexity = gameSession.getComplexity();
        gameData.gameStatus = gameSession.getGameStatus();
        gameData.assumedMineRemainder = gameSession.getField().getAssumedMinesRemainder();
        gameData.icons = extractIconsFromSession(gameSession);
        gameData.isRecord = gameSession.getCurrentSessionDuration() < gameSession.getRecordDuration();
        return gameData;
    }

    private static CellIcon[][] extractIconsFromSession(GameSession gameSession) {
        int size = gameSession.getComplexity().getSize();
        CellIcon[][] icons = new CellIcon[size][size];

        Cell[][] cells = gameSession.getField().getCells();

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                icons[r][c] = cells == null ? CellIcon.UNKNOWN : cells[r][c].getIcon();
            }
        }

        return icons;
    }

    public CellIcon[][] getIcons() {
        return icons;
    }

    public Complexity getComplexity() {
        return complexity;
    }

    public int getAssumedMineRemainder() {
        return assumedMineRemainder;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public boolean isRecord() {
        return isRecord;
    }
}
