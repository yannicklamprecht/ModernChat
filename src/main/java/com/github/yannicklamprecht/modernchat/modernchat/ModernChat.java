package com.github.yannicklamprecht.modernchat.modernchat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.yannicklamprecht.modernchat.modernchat.config.ModernChatConfig;
import com.github.yannicklamprecht.modernchat.modernchat.providers.JobsProvider;
import com.github.yannicklamprecht.modernchat.modernchat.providers.LuckpermsProvider;
import net.kyori.adventure.text.minimessage.Template;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public final class ModernChat extends JavaPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModernChat.class);

    @Override
    public void onEnable() {


        Path file;
        try {
            file = getDataFolder().toPath().resolve(Path.of("config.yml"));
            if(!Files.exists(getDataFolder().toPath().resolve("config.yml"))){
                Files.createDirectory(getDataFolder().toPath());
                Files.createFile(file);
            }
        } catch (IOException e) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

        var config = new ModernChatConfig();
        try {
            config = objectMapper.readValue(file.toFile(), ModernChatConfig.class);
        } catch (IOException e) {
            LOGGER.warn("Could not load modern chat config from file, writing default values");
            try {
                objectMapper.writeValue(file.toFile(), config);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        var provider = new HashSet<TemplateProvider>();

        RegisteredServiceProvider<LuckPerms> luckPermsRegisteredServiceProvider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (luckPermsRegisteredServiceProvider != null) {
            provider.add(new LuckpermsProvider(luckPermsRegisteredServiceProvider.getProvider()));
        }

        provider.add(new JobsProvider());
        provider.add((source, sourceDisplayName, message, viewer) -> Set.of(Template.of("name", sourceDisplayName)));
        provider.add((source, sourceDisplayName, message, viewer) -> Set.of(Template.of("message", message)));

        var chatListener = new ChatListener(provider, config.chatformat());
        getServer().getPluginManager().registerEvents(chatListener, this);

    }

    @Override
    public void onDisable() {
    }
}
