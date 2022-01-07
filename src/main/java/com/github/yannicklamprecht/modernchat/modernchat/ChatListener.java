package com.github.yannicklamprecht.modernchat.modernchat;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.Template;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public record ChatListener(
        Set<TemplateProvider> templateProviders,
        Set<TemplateResolver> templateResolvers,
        String format) implements Listener {

    @EventHandler
    public void onBetterChat(AsyncChatEvent asyncChatEvent) {
        asyncChatEvent.renderer((source, sourceDisplayName, message, viewer) ->
                MiniMessage.builder()
                        .placeholderResolver(resolveFor(source, sourceDisplayName, message, viewer))
                        .build()
                        .parse(format, templatesFor(source, sourceDisplayName, message, viewer)));
    }

    private List<Template> templatesFor(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        List<Template> templates = new ArrayList<>();
        for (TemplateProvider templateProvider : templateProviders) {
            templates.addAll(templateProvider.templatesFor(source, sourceDisplayName, message, viewer));
        }
        return templates;
    }

    private Function<String, ComponentLike> resolveFor(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        return (name) -> templateResolvers.stream()
                .map(templateResolver -> templateResolver.resolve(name, source, sourceDisplayName, message, viewer))
                .findFirst()
                .orElse(null);
    }
}
