package com.github.yannicklamprecht.modernchat.modernchat;

import com.gamingmesh.jobs.Jobs;
import com.github.yannicklamprecht.modernchat.modernchat.config.ConfigLoader;
import com.github.yannicklamprecht.modernchat.modernchat.config.ModernChatConfig;
import com.github.yannicklamprecht.modernchat.modernchat.providers.DefaultProvider;
import com.github.yannicklamprecht.modernchat.modernchat.providers.JobsProvider;
import com.github.yannicklamprecht.modernchat.modernchat.providers.LuckpermsProvider;
import com.github.yannicklamprecht.modernchat.modernchat.resolver.PlaceholderApiResolver;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashSet;

public final class ModernChat extends JavaPlugin {
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
        var resolver = new HashSet<TemplateResolver>();

        RegisteredServiceProvider<LuckPerms> luckPermsRegisteredServiceProvider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (luckPermsRegisteredServiceProvider != null) {
            provider.add(new LuckpermsProvider(luckPermsRegisteredServiceProvider.getProvider()));
        }

        RegisteredServiceProvider<Jobs> jobsRegisteredServiceProvider = Bukkit.getServicesManager().getRegistration(Jobs.class);
        if (jobsRegisteredServiceProvider != null) {
            provider.add(new JobsProvider());
        }

        provider.add(new JobsProvider());
        provider.add(new DefaultProvider());

        RegisteredServiceProvider<PlaceholderAPIPlugin> placeholderAPIPluginRegisteredServiceProvider = Bukkit.getServicesManager().getRegistration(PlaceholderAPIPlugin.class);
        if (placeholderAPIPluginRegisteredServiceProvider != null) {
            resolver.add(new PlaceholderApiResolver());
        }

        var chatListener = new ChatListener(provider, resolver, config.chatformat());
        getServer().getPluginManager().registerEvents(chatListener, this);
    }

    @Override
    public void onDisable() {
    }
}
