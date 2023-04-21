package org.eugenible.view.viewListeners;

import org.eugenible.view.elements.ButtonType;

public interface CellEventListener {
    void onMouseClick(int x, int y, ButtonType buttonType);
}
