package com.ismail.homesystem.api.mysql.models;

import com.ismail.homesystem.api.mysql.models.compositekeys.PlayerHouseCompositeKey;
import jakarta.persistence.*;

@Entity
@IdClass(PlayerHouseCompositeKey.class)
public class PlayerHouse {
    @Id
    private String homeName;

    @Id
    private String playerUUID;
    private String coordinates;


    public PlayerHouse(String homeName, String playerUUID, String coordinates) {
        this.homeName = homeName;
        this.playerUUID = playerUUID;
        this.coordinates = coordinates;
    }

    public PlayerHouse() {
    }

    @Override
    public String toString() {
        return "PlayerHouse [playerId=" + playerUUID + ", coordinates=" + coordinates;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setPlayerUUID(String playerId) {
        this.playerUUID = playerId;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }
}