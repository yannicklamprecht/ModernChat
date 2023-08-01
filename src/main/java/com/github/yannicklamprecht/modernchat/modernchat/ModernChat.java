package com.github.yannicklamprecht.modernchat.modernchat;

import com.github.yannicklamprecht.modernchat.modernchat.config.ConfigLoader;
import com.github.yannicklamprecht.modernchat.modernchat.config.ModernChatConfig;
import com.github.yannicklamprecht.modernchat.modernchat.preprocessing.PlaceholderApiPreprocessor;
import com.github.yannicklamprecht.modernchat.modernchat.providers.DefaultProvider;
import com.github.yannicklamprecht.modernchat.modernchat.providers.JobsProvider;
import com.github.yannicklamprecht.modernchat.modernchat.providers.LuckpermsProvider;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;

public final class ModernChat extends JavaPlugin {
    private final Logger LOGGER = LoggerFactory.getLogger(ModernChat.class);

    @Override
    public void onEnable() {

        var configLoader = new ConfigLoader(getDataFolder().toPath());

        ModernChatConfig config;
        try {
            config = configLoader.load();
        } catch (IOException e) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        var provider = new HashSet<TemplateProvider>();
        var preprocessor = new HashSet<FormatPreprocessor>();

        provider.add(new DefaultProvider());

        RegisteredServiceProvider<LuckPerms> luckPermsRegisteredServiceProvider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (luckPermsRegisteredServiceProvider != null) {
            LOGGER.info("Luckperms Integration added.");
            provider.add(new LuckpermsProvider(luckPermsRegisteredServiceProvider.getProvider()));
        }

        if (getServer().getPluginManager().getPlugin("Jobs") != null) {
            LOGGER.info("Jobs Integration added.");
            provider.add(new JobsProvider());
        }

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            LOGGER.info("PAPI Integration added.");
            preprocessor.add(new PlaceholderApiPreprocessor());
        }



        TranslationRegistry message = TranslationRegistry.create(Key.key("message"));
        message.registerAll(Locale.GERMAN, ResourceBundle.getBundle("benis", Locale.GERMAN), true);
        message.registerAll(Locale.ENGLISH, ResourceBundle.getBundle("benis", Locale.ENGLISH), true);
        message.defaultLocale(Locale.GERMAN);
        GlobalTranslator.translator().addSource(message);

        var chatListener = new ChatListener(provider, preprocessor, config.chatformat());
        getServer().getPluginManager().registerEvents(chatListener, this);
    }

    @Override
    public void onDisable() {
    }
}
