package com.github.yannicklamprecht.modernchat.modernchat;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

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
                    return MiniMessage.miniMessage()
                            .deserialize(PlainTextComponentSerializer.plainText().serialize(MiniMessage.miniMessage()
                                    .deserialize(messageFormat, templatesFor(source, sourceDisplayName, message, viewer))),
                                    templatesFor(source, sourceDisplayName, message, viewer));
                }
        );
    }

    private TagResolver templatesFor(@NotNull Player source, @NotNull Component sourceDisplayName,
                                     @NotNull Component message, @NotNull Audience viewer) {
        List<TagResolver> templates = new ArrayList<>();
        for (TemplateProvider templateProvider : templateProviders) {
            templates.add(templateProvider.templatesFor(source, sourceDisplayName, message, viewer));
        }
        return TagResolver.resolver(templates);
    }
}
