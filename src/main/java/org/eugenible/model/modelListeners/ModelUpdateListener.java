package org.eugenible.model.modelListeners;

import org.eugenible.model.interaction.GameData;

@FunctionalInterface
public interface ModelUpdateListener {
    void onModelUpdate(GameData data);
}
