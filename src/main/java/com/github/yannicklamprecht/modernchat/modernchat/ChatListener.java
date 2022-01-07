package com.github.yannicklamprecht.modernchat.modernchat;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.placeholder.PlaceholderResolver;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record ChatListener(
        Set<TemplateProvider> templateProviders,
        Set<FormatPreprocessor> preprocessors,
        String format) implements Listener {

    @EventHandler
    public void onBetterChat(AsyncChatEvent asyncChatEvent) {
        asyncChatEvent.renderer((source, sourceDisplayName, message, viewer) -> {
                    var messageFormat = format;
                    for (FormatPreprocessor preprocessor : preprocessors) {
                        messageFormat = preprocessor.resolve(source, messageFormat);
                    }

                    return MiniMessage.builder()
                            .placeholderResolver(templatesFor(source, sourceDisplayName, message, viewer))
                            .build().parse(messageFormat);
                }

        );
    }

    private PlaceholderResolver templatesFor(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        List<PlaceholderResolver> templates = new ArrayList<>();
        for (TemplateProvider templateProvider : templateProviders) {
            templates.add(templateProvider.templatesFor(source, sourceDisplayName, message, viewer));
        }
        return PlaceholderResolver.combining(templates);
    }
}
