package com.github.yannicklamprecht.modernchat.modernchat.providers;

import com.github.yannicklamprecht.modernchat.modernchat.TemplateProvider;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Template;
import net.luckperms.api.LuckPerms;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public record LuckpermsProvider(LuckPerms luckPerms) implements TemplateProvider {

    @Override
    public Set<Template> templatesFor(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        var templates = new HashSet<Template>();

        var user = luckPerms.getUserManager().getUser(source.getUniqueId());

        if (user != null) {
            var meta = user.getCachedData().getMetaData();
            templates.add(Template.of("prefix", meta.getPrefix()));
            templates.add(Template.of("suffix", meta.getSuffix()));
        }
        return templates;
    }
}
