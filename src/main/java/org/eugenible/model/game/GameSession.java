package org.eugenible.model.game;


import lombok.AccessLevel;
import lombok.Getter;
import org.eugenible.model.interaction.GameSessionTimer;
import org.eugenible.model.interaction.GameStatus;
import org.eugenible.model.modelListeners.TimerListener;

import java.util.List;

@Getter
// Объект, связанный с конкретной игровой попыткой
public class GameSession {
    private final Complexity complexity;
    private final @Getter(AccessLevel.NONE) GameSessionTimer timer;
    private final Field field;
    private final int recordDuration;
    private GameStatus gameStatus;

    public GameSession(Complexity complexity, List<TimerListener> timerListeners, int recordDuration) {
        this.timer = new GameSessionTimer(timerListeners);
        this.gameStatus = GameStatus.NEW;
        this.complexity = complexity;
        this.field = new Field(complexity.getSize(), complexity.getSize(), complexity.getBombs());
        this.recordDuration = recordDuration;
    }

    public void leftMouseClicked(int x, int y) {
        if (gameStatus.equals(GameStatus.LOST) || gameStatus.equals(GameStatus.WON)) {
            return;
        }

        if (gameStatus.equals(GameStatus.NEW)) {
            gameStatus = GameStatus.RUNNING;
            initializeField(x, y);
            timer.startTimer();
        }

        field.leftMouseClick(x, y);
        winGameIfSafeCellsAreOpened();
        endGameIfExploded();
    }

    private void initializeField(int x, int y) {
        field.generateMineCoordinates(x, y);
        field.placeMines();
        field.countBombsAroundSafeCells();
    }

    private void winGameIfSafeCellsAreOpened() {
        if (field.getSafeCellRemainder() == 0) {
            field.markAllMinesWithIcon(CellIcon.FLAGGED);
            field.setAssumedMinesRemainder(0);
            gameStatus = GameStatus.WON;
            timer.stopTimer();
        }
    }

    public void middleMouseClicked(int x, int y) {
        if (gameStatus.equals(GameStatus.RUNNING)) {
            field.middleMouseClick(x, y);
        }
        winGameIfSafeCellsAreOpened();
        endGameIfExploded();
    }

    public void rightMouseClicked(int x, int y) {
        if (gameStatus.equals(GameStatus.RUNNING) || gameStatus.equals(GameStatus.NEW)) {
            field.rightMouseClick(x, y);
        }
    }

    private void endGameIfExploded() {
        if (field.isBombExploded()) {
            gameStatus = GameStatus.LOST;
            field.markAllMinesWithIcon(CellIcon.BOMB_SHOW);
            timer.stopTimer();
        }
    }

    public int getCurrentSessionDuration() {
        return timer.getCurrentGameDuration();
    }
}
