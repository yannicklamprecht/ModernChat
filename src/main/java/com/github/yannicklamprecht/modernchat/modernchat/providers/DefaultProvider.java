package com.github.yannicklamprecht.modernchat.modernchat.providers;

import com.github.yannicklamprecht.modernchat.modernchat.TemplateProvider;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DefaultProvider implements TemplateProvider {
    @Override
    public TagResolver templatesFor(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        return TagResolver.resolver(
                Placeholder.component("name", sourceDisplayName),
                Placeholder.component("display-name", source.displayName()),
                Placeholder.component("uuid", Component.text(source.getUniqueId().toString())),
                Placeholder.component("message", message),
                Placeholder.component("custom-player-name", sourceDisplayName)
        );
    }
}
