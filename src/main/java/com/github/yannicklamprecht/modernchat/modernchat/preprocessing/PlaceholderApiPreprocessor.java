package com.github.yannicklamprecht.modernchat.modernchat.preprocessing;

import com.github.yannicklamprecht.modernchat.modernchat.FormatPreprocessor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderApiPreprocessor implements FormatPreprocessor {

    @Override
    public @NotNull String resolve(@NotNull Player player, @NotNull String format) {
        return PlaceholderAPI.setPlaceholders(player, format);
    }
}
