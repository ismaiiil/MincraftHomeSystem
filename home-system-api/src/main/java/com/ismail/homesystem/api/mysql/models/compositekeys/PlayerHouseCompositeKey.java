package com.ismail.homesystem.api.mysql.models.compositekeys;

import java.io.Serializable;
import java.util.Objects;

public class PlayerHouseCompositeKey implements Serializable {
    protected String homeName;
    protected String playerUUID;

    public PlayerHouseCompositeKey() {}

    public PlayerHouseCompositeKey(String homeName, String playerUUID) {
        this.homeName = homeName;
        this.playerUUID = playerUUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerHouseCompositeKey that = (PlayerHouseCompositeKey) o;
        return Objects.equals(homeName, that.homeName) &&
                Objects.equals(playerUUID, that.playerUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeName, playerUUID);
    }
}
