package com.github.yannicklamprecht.modernchat.modernchat.providers;

import com.github.yannicklamprecht.modernchat.modernchat.TemplateProvider;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.luckperms.api.LuckPerms;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Optional;

public record LuckpermsProvider(LuckPerms luckPerms) implements TemplateProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(LuckpermsProvider.class);

    @Override
    public TagResolver templatesFor(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        var user = luckPerms.getUserManager().getUser(source.getUniqueId());

        if (user != null) {
            var meta = user.getCachedData().getMetaData();
            var templates = new HashSet<TagResolver>();
            templates.add(Placeholder.parsed("prefix", Optional.ofNullable(meta.getPrefix()).orElse("")));
            templates.add(Placeholder.parsed("suffix", Optional.ofNullable(meta.getSuffix()).orElse("")));
            return TagResolver.resolver(templates);
        }
        return TagResolver.empty();
    }
}
