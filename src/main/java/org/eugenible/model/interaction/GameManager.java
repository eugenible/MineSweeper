package org.eugenible.model.interaction;

import org.eugenible.model.game.Complexity;
import org.eugenible.model.game.GameSession;
import org.eugenible.model.modelListeners.ModelUpdateListener;
import org.eugenible.model.modelListeners.RecordsUpdateListener;
import org.eugenible.model.modelListeners.TimerListener;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    List<ModelUpdateListener> modelUpdateListeners = new ArrayList<>();
    List<TimerListener> timerListeners = new ArrayList<>();
    List<RecordsUpdateListener> recordsUpdateListeners = new ArrayList<>();

    private Complexity nextGameComplexity;
    private GameSession currentGameSession;
    private RecordManager recordManager;

    public GameManager() {
        recordManager = new RecordManager("records.txt");
    }

    public void startNewGame() {
        int recordDuration = recordManager.getRecordPlayerData(nextGameComplexity).getDuration();
        currentGameSession = new GameSession(nextGameComplexity, timerListeners, recordDuration);
        notifyModelUpdateListeners(GameData.getDataFromSession(currentGameSession));
        notifyRecordsUpdateListeners();
    }

    public void mouseClick(MouseButton button, int x, int y) {
        if (isGameSessionFinished(currentGameSession)) return;
        switch (button) {
            case LEFT -> currentGameSession.leftMouseClicked(x, y);
            case MIDDLE -> currentGameSession.middleMouseClicked(x, y);
            case RIGHT -> currentGameSession.rightMouseClicked(x, y);
        }
        notifyModelUpdateListeners(GameData.getDataFromSession(currentGameSession));
    }

    private boolean isGameSessionFinished(GameSession session) {
        return session.getGameStatus().equals(GameStatus.WON) || session.getGameStatus().equals(GameStatus.LOST);
    }

    public void notifyModelUpdateListeners(GameData data) {
        for (ModelUpdateListener listener : modelUpdateListeners) {
            listener.onModelUpdate(data);
        }
    }

    public void updateRecordList(String name) {
        recordManager.updateRecord(currentGameSession.getComplexity(),
                new PlayerData(name, currentGameSession.getCurrentSessionDuration()));
        notifyRecordsUpdateListeners();
    }

    public void setNextGameComplexity(Complexity nextGameComplexity) {
        this.nextGameComplexity = nextGameComplexity;
    }

    public void addModelUpdateListener(ModelUpdateListener listener) {
        modelUpdateListeners.add(listener);
    }

    public void addTimerListener(TimerListener timerListener) {
        timerListeners.add(timerListener);
    }

    public void addRecordsUpdateListeners(RecordsUpdateListener recordsUpdateListener) {
        recordsUpdateListeners.add(recordsUpdateListener);
    }

    public void notifyRecordsUpdateListeners() {
        for (RecordsUpdateListener listener : recordsUpdateListeners) {
            listener.onRecordsListUpdated(recordManager.getRecordsMap());
        }
    }
}
