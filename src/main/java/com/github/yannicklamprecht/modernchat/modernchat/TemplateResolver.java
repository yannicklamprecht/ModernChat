package com.github.yannicklamprecht.modernchat.modernchat;


import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface TemplateResolver {
    @Nullable ComponentLike resolve(@NotNull String name, @NotNull Player player, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer);
}
