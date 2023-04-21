package org.eugenible.model.modelListeners;

import org.eugenible.model.game.Complexity;
import org.eugenible.model.interaction.PlayerData;

import java.util.Map;

public interface RecordsUpdateListener {
    void onRecordsListUpdated(Map<Complexity, PlayerData> records);
}
