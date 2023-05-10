package org.eugenible.view;

import lombok.Setter;
import org.eugenible.model.game.CellIcon;
import org.eugenible.model.game.Complexity;
import org.eugenible.model.interaction.GameData;
import org.eugenible.model.interaction.PlayerData;
import org.eugenible.model.modelListeners.ModelUpdateListener;
import org.eugenible.model.modelListeners.RecordsUpdateListener;
import org.eugenible.model.modelListeners.TimerListener;
import org.eugenible.view.elements.*;
import org.eugenible.view.viewListeners.CellEventListener;
import org.eugenible.view.viewListeners.GameTypeListener;
import org.eugenible.view.viewListeners.NewGameListener;
import org.eugenible.view.viewListeners.RecordNameListener;

import java.util.Map;

public class View implements ModelUpdateListener, TimerListener, RecordsUpdateListener {

    private CellEventListener cellEventListener;
    private GameTypeListener gameTypeListener;
    private RecordNameListener recordNameListener;
    private NewGameListener newGameListener;

    private MainWindow mainWindow;
    private LoseWindow loseWindow;
    private WinWindow winWindow;
    private RecordsWindow recordsWindow;
    private HighScoresWindow highScoresWindow;

    public View(CellEventListener cellEventListener, GameTypeListener gameTypeListener,
                RecordNameListener recordNameListener, NewGameListener newGameListener) {
        this.cellEventListener = cellEventListener;
        this.gameTypeListener = gameTypeListener;
        this.recordNameListener = recordNameListener;
        this.newGameListener = newGameListener;
    }

    public void startGUI() {
        setupViewElements();
        mainWindow.setVisible(true);
    }

    private void setupViewElements() {
        mainWindow = buildMainWindow();
        loseWindow = buildLoseWindow(mainWindow);
        winWindow = buildWinWindow(mainWindow);
        recordsWindow = new RecordsWindow(mainWindow);
        recordsWindow.setNameListener(recordNameListener);
        gameTypeListener.onGameTypeChanged(GameType.getDefault());
    }

    public MainWindow buildMainWindow() {
        MainWindow mainWindow = new MainWindow();
        SettingsWindow settingsWindow = new SettingsWindow(mainWindow);
        settingsWindow.setGameTypeListener(gameTypeListener);

        HighScoresWindow highScoresWindow = new HighScoresWindow(mainWindow);
        this.highScoresWindow = highScoresWindow;

        mainWindow.setSettingsMenuAction(e -> settingsWindow.setVisible(true));

        mainWindow.setHighScoresMenuAction(e ->
                highScoresWindow.setVisible(true)
        );

        mainWindow.setExitMenuAction(e -> mainWindow.exit());

        mainWindow.setNewGameMenuAction(
                e -> newGameListener.onNewGameClicked());  // расстановка мин, запуск таймера и т.д.
        mainWindow.setCellListener(cellEventListener);

        return mainWindow;
    }

    private LoseWindow buildLoseWindow(MainWindow mainWindow) {
        LoseWindow loseWindow = new LoseWindow(mainWindow);
        loseWindow.setNewGameListener(e -> newGameListener.onNewGameClicked());
        loseWindow.setExitListener(e -> mainWindow.exit());
        return loseWindow;
    }

    private WinWindow buildWinWindow(MainWindow mainWindow) {
        WinWindow winWindow = new WinWindow(mainWindow);
        winWindow.setNewGameListener(e -> newGameListener.onNewGameClicked());
        winWindow.setExitListener(e -> mainWindow.exit());
        return winWindow;
    }


    @Override
    public void onModelUpdate(GameData gameData) {
        if (mainWindow == null) return;
        switch (gameData.getGameStatus()) {
            case RUNNING -> updateField(gameData);
            case NEW -> createField(gameData);
            case LOST -> {
                updateField(gameData);
                loseWindow.setVisible(true);
            }
            case WON -> {
                updateField(gameData);
                if (gameData.isRecord()) recordsWindow.setVisible(true);
                winWindow.setVisible(true);
            }
        }
    }

    private void createField(GameData gameData) {
        int size = gameData.getComplexity().getSize();
        mainWindow.createGameField(size, size);
        updateField(gameData);
    }

    private void updateField(GameData gameData) {
        mainWindow.setBombsCount(gameData.getAssumedMineRemainder());
        CellIcon[][] icons = gameData.getIcons();
        for (int r = 0; r < icons.length; r++) {
            for (int c = 0; c < icons[r].length; c++) {
                mainWindow.setCellImage(r, c, getImageFromIcon(icons[r][c]));
            }
        }
    }

    private GameImage getImageFromIcon(CellIcon icon) {
        return switch (icon) {
            case UNKNOWN -> GameImage.CLOSED;
            case FLAGGED -> GameImage.MARKED;
            case BOMB_SHOW -> GameImage.BOMB;
            case ZERO -> GameImage.EMPTY;
            case ONE -> GameImage.NUM_1;
            case TWO -> GameImage.NUM_2;
            case THREE -> GameImage.NUM_3;
            case FOUR -> GameImage.NUM_4;
            case FIVE -> GameImage.NUM_5;
            case SIX -> GameImage.NUM_6;
            case SEVEN -> GameImage.NUM_7;
            case EIGHT -> GameImage.NUM_8;
        };
    }

    @Override
    public void onTimerTicked(int seconds) {
        mainWindow.setTimerValue(seconds);
    }

    @Override
    public void onRecordsListUpdated(Map<Complexity, PlayerData> records) {
        for (Complexity complexity : Complexity.values()) {
            PlayerData player = records.get(complexity);
            switch (complexity) {
                case EASY -> highScoresWindow.setNoviceRecord(player.getPlayerName(), player.getDuration());
                case MEDIUM -> highScoresWindow.setMediumRecord(player.getPlayerName(), player.getDuration());
                case HARD -> highScoresWindow.setExpertRecord(player.getPlayerName(), player.getDuration());
            }
        }
    }
}
