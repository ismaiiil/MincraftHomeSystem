package com.ismail.homesystem.api.gson.models;

public class DatabaseConfig {
    private String databaseURL;
    private String user;
    private String password;

    private String ddlMode;

    public DatabaseConfig() {
    }

    public String getDatabaseURL() {
        return databaseURL;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setDatabaseURL(String databaseURL) {
        this.databaseURL = databaseURL;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDdlMode() {
        return ddlMode;
    }

    public void setDdlMode(String ddlMode) {
        this.ddlMode = ddlMode;
    }
}
