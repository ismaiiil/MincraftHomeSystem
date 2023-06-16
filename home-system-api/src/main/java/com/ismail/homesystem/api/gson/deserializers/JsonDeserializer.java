package com.ismail.homesystem.api.gson.deserializers;

import com.google.gson.Gson;
import com.ismail.homesystem.api.gson.models.DatabaseConfig;

import java.io.FileReader;
import java.io.IOException;

public class JsonDeserializer {

    private DatabaseConfig databaseConfig;

    public JsonDeserializer(String path) {
        try (FileReader reader = new FileReader(path)) {
            Gson gson = new Gson();
            databaseConfig = gson.fromJson(reader, DatabaseConfig.class);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }
}
