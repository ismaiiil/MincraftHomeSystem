package com.ismail.homesystem.api.gson.deserializers;

import com.google.gson.Gson;
import com.ismail.homesystem.api.gson.models.DatabaseConfig;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;

public class JsonDeserializer {
    private DatabaseConfig databaseConfig;
    public JsonDeserializer(String jsonFilename) {
        String workingDirectory = new File("").getAbsolutePath();
        String sep = FileSystems.getDefault().getSeparator();
        String pluginsFolder =  sep + "plugins" + sep;
        try (FileReader reader = new FileReader(workingDirectory + pluginsFolder + jsonFilename)) {
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
