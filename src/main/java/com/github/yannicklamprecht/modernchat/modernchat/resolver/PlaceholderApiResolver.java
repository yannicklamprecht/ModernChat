package com.github.yannicklamprecht.modernchat.modernchat.resolver;

import com.github.yannicklamprecht.modernchat.modernchat.TemplateResolver;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlaceholderApiResolver implements TemplateResolver {
    private final Logger LOGGER = LoggerFactory.getLogger(PlaceholderApiResolver.class);

    @Override
    public @Nullable ComponentLike resolve(@NotNull String name, @NotNull Player player, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        var replaced = PlaceholderAPI.setPlaceholders(player, name);
        if(!replaced.equals(name)){
            return Component.text(replaced);
        }
        return null;
    }
}
