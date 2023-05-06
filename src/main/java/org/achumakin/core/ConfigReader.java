package org.achumakin.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.achumakin.model.ConfigModel;

import java.io.IOException;

public class ConfigReader {

    private static final String CONFIG_NAME = "config.yml";
    private static ConfigModel config;

    public static ConfigModel getConfig() {
        if (config == null) {
            config = loadConfigYaml();
        }
        return config;
    }

    private static ConfigModel loadConfigYaml() {
        try {
            var configResourceStream = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_NAME);
            var mapper = new ObjectMapper(new YAMLFactory());
            return mapper.readValue(configResourceStream, ConfigModel.class);
        } catch (IOException ex) {
            throw new RuntimeException("Cannot read config", ex);
        }
    }

}
