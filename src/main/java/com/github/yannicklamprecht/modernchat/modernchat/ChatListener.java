package com.github.yannicklamprecht.modernchat.modernchat;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.Template;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record ChatListener(
        Set<TemplateProvider> templateProviders,
        String format) implements Listener {

    @EventHandler
    public void onBetterChat(AsyncChatEvent asyncChatEvent) {
        asyncChatEvent.renderer((source, sourceDisplayName, message, viewer) ->
                MiniMessage.builder().build().parse(format, templatesFor(source, sourceDisplayName, message, viewer)));
    }

    private List<Template> templatesFor(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        List<Template> templates = new ArrayList<>();
        for (TemplateProvider templateProvider : templateProviders) {
            templates.addAll(templateProvider.templatesFor(source, sourceDisplayName, message, viewer));
        }
        return templates;
    }

}
