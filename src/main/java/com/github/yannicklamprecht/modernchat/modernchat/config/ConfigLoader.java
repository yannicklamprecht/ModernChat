package com.github.yannicklamprecht.modernchat.modernchat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record ConfigLoader(Path dataFolder) {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLoader.class);

    public ModernChatConfig load() throws IOException {
        Path file;

        file = dataFolder.resolve(Path.of("config.yml"));
        if (!Files.exists(dataFolder.resolve("config.yml"))) {
            Files.createDirectory(dataFolder);
            Files.createFile(file);
        }

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

        var config = new ModernChatConfig();
        try {
            config = objectMapper.readValue(file.toFile(), ModernChatConfig.class);
        } catch (IOException e) {
            LOGGER.warn("Could not load modern chat config from file, writing default values");
            objectMapper.writeValue(file.toFile(), config);
        }
        return config;
    }

}
