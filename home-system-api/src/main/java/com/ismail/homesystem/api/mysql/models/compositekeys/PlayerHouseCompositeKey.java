package com.ismail.homesystem.api.mysql.models.compositekeys;

import java.io.Serializable;

public class PlayerHouseCompositeKey implements Serializable {
    protected String homeName;
    protected String playerUUID;

    public PlayerHouseCompositeKey() {}

    public PlayerHouseCompositeKey(String homeName, String playerUUID) {
        this.homeName = homeName;
        this.playerUUID = playerUUID;
    }
}
