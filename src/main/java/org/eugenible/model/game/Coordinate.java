package org.eugenible.model.game;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public final class Coordinate {
    private final int x;
    private final int y;
}
