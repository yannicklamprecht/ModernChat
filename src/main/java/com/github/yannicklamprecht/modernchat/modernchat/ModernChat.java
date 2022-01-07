package com.github.yannicklamprecht.modernchat.modernchat;

import com.github.yannicklamprecht.modernchat.modernchat.providers.Jobs;
import com.github.yannicklamprecht.modernchat.modernchat.providers.LuckpermsProvider;
import net.kyori.adventure.text.minimessage.Template;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public final class ModernChat extends JavaPlugin {

    @Override
    public void onEnable() {

        var provider = Set.of(
                new Jobs(),
                new LuckpermsProvider(),
                (source, sourceDisplayName, message, viewer) -> Set.of(Template.of("name", sourceDisplayName)),
                (source, sourceDisplayName, message, viewer) -> Set.of(Template.of("message", message))
        );

        var chatListener = new ChatListener(provider, "<gray>[<job>|<group>] <name> >> <message>");
        getServer().getPluginManager().registerEvents(chatListener, this);

    }

    @Override
    public void onDisable() {
    }
}
