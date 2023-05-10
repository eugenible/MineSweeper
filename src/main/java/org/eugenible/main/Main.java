package org.eugenible.main;

import org.eugenible.controllers.CellEventController;
import org.eugenible.controllers.GameTypeController;
import org.eugenible.controllers.NewGameController;
import org.eugenible.controllers.RecordNameController;
import org.eugenible.model.interaction.GameManager;
import org.eugenible.model.interaction.RecordManager;
import org.eugenible.view.View;

public class Main {

    public static void main(String[] args) {
        RecordManager recordManager = new RecordManager("records.txt");
        GameManager manager = new GameManager(recordManager);

        CellEventController cellEventController = new CellEventController(manager);
        GameTypeController gameTypeController = new GameTypeController(manager);
        NewGameController newGameController = new NewGameController(manager);
        RecordNameController recordNameController = new RecordNameController(manager);

        View view = new View(cellEventController, gameTypeController, recordNameController, newGameController);

        manager.addModelUpdateListener(view);
        manager.addTimerListener(view);
        manager.addRecordsUpdateListeners(view);

        view.startGUI();
    }
}
