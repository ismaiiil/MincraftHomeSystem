package com.ismail.homesystem.api.mysql.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PlayerHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String playerId;
    private String coordinates;

    public PlayerHouse(String playerId, String coordinates) {
        this.playerId = playerId;
        this.coordinates = coordinates;
    }

    public PlayerHouse() {
    }

    @Override
    public String toString() {
        return "PlayerHouse [id=" + id + ", playerId=" + playerId + ", coordinates=" + coordinates;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}