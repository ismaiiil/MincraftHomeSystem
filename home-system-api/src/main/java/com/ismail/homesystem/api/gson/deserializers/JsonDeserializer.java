package com.ismail.homesystem.api.gson.deserializers;

import com.google.gson.Gson;
import com.ismail.homesystem.api.gson.models.ConfigWrapper;
import com.ismail.homesystem.api.gson.models.DatabaseConfig;
import com.ismail.homesystem.common.OSUtils;
import com.ismail.homesystem.common.StringUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonDeserializer {
    private ConfigWrapper configWrapper;

    public JsonDeserializer(String jsonFilename) {
        String workingDirectory = new File("").getAbsolutePath();
        String sep = FileSystems.getDefault().getSeparator();
        String pluginsFolder =  sep + "plugins" + sep;
        try{
            Gson gson = new Gson();
            String fileContents = Files.readString(Paths.get(workingDirectory + pluginsFolder + jsonFilename), StandardCharsets.UTF_8);
//            fileContents = StringUtils.removeLineEndings(fileContents);
            configWrapper = gson.fromJson(fileContents, ConfigWrapper.class);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public ConfigWrapper getConfigWrapper() {
        return configWrapper;
    }
}
