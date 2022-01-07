package com.github.yannicklamprecht.modernchat.modernchat.providers;

import com.github.yannicklamprecht.modernchat.modernchat.TemplateProvider;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.placeholder.Placeholder;
import net.kyori.adventure.text.minimessage.placeholder.PlaceholderResolver;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DefaultProvider implements TemplateProvider {
    @Override
    public PlaceholderResolver templatesFor(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        return PlaceholderResolver.placeholders(
                Placeholder.component("name", sourceDisplayName),
                Placeholder.component("display-name", source.displayName()),
                Placeholder.component("uuid", Component.text(source.getUniqueId().toString())),
                Placeholder.component("message", message)
        );
    }
}
