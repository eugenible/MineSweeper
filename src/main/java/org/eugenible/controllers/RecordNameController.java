package org.eugenible.controllers;

import org.eugenible.model.interaction.GameManager;
import org.eugenible.view.viewListeners.RecordNameListener;

public class RecordNameController implements RecordNameListener {
    private final GameManager manager;

    public RecordNameController(GameManager manager) {
        this.manager = manager;
    }

    @Override
    public void onRecordNameEntered(String name) {
        manager.updateRecordList(name);
    }
}
