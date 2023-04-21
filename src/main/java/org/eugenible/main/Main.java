package org.eugenible.main;

import org.eugenible.controller.Controller;
import org.eugenible.model.interaction.GameManager;
import org.eugenible.view.View;

public class Main {

    public static void main(String[] args) {
        GameManager manager = new GameManager();

        Controller controller = new Controller(manager);

        View view = new View(controller, controller, controller, controller);

        manager.addModelUpdateListener(view);
        manager.addTimerListener(view);
        manager.addRecordsUpdateListeners(view);

        view.startGUI();
    }
}
