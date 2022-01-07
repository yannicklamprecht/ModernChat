package com.github.yannicklamprecht.modernchat.modernchat.resolver;

import com.github.yannicklamprecht.modernchat.modernchat.TemplateResolver;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceholderApiResolver implements TemplateResolver {

    @Override
    public @Nullable ComponentLike resolve(@NotNull String name, @NotNull Player player, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        String replaced = PlaceholderAPIPlugin.getInstance().getLocalExpansionManager().getExpansion(name).onRequest(player, name);
        if(replaced != null){
            return Component.text(replaced);
        }
        return null;
    }
}
