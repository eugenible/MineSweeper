package org.eugenible.model.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Complexity {
    EASY(9, 10),
    MEDIUM(16, 40),
    HARD(24, 99);

    private final int size;
    private final int bombs;
}
