package com.ismail.homesystem.api.mysql.models;

import com.ismail.homesystem.api.mysql.models.compositekeys.PlayerHouseCompositeKey;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@IdClass(PlayerHouseCompositeKey.class)
public class PlayerHouse {
    @Id
    private String homeName;

    @Id
    private UUID playerUUID;
    private String coordinates;


    public PlayerHouse(String homeName, UUID playerUUID, String coordinates) {
        this.homeName = homeName;
        this.playerUUID = playerUUID;
        this.coordinates = coordinates;
    }

    public PlayerHouse() {
    }

    @Override
    public String toString() {
        return "PlayerHouse [playerId=" + playerUUID.toString() + ", coordinates=" + coordinates;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setPlayerUUID(UUID playerId) {
        this.playerUUID = playerId;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

}