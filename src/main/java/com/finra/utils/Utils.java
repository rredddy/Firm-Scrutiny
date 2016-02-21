package com.finra.utils;

import com.finra.config.FirmConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class to load the config yml file and populates FirmConfig Object.
 * Created by ravender on 2/19/2016.
 */
public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);
    private static FirmConfig config;

    static {
        Path configFile = Paths.get("src/main/resources/finra_config.yml");
        try (InputStream in = Files.newInputStream(configFile)) {
            Yaml yaml = new Yaml();
            config = yaml.loadAs(in, FirmConfig.class);
        } catch (IOException exception) {
            LOGGER.error("Problem while loading the config file {}", exception);
            throw new RuntimeException(exception);
        }
    }

    public FirmConfig getConfig() {
        return config;
    }
}
