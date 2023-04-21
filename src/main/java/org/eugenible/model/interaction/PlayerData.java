package org.eugenible.model.interaction;

public class PlayerData {
    private final String playerName;
    private final int duration;

    public PlayerData(String playerName, int duration) {
        this.playerName = playerName;
        this.duration = duration;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getDuration() {
        return duration;
    }

}
