package com.ismail.homesystem.api.gson.models;

public class ConfigWrapper {
    private DatabaseConfig databaseConfig;

    public ConfigWrapper() {
    }

    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }
}
