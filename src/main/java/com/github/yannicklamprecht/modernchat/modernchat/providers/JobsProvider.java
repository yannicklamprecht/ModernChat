package com.github.yannicklamprecht.modernchat.modernchat.providers;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.JobProgression;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.github.yannicklamprecht.modernchat.modernchat.TemplateProvider;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.placeholder.Placeholder;
import net.kyori.adventure.text.minimessage.placeholder.PlaceholderResolver;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashSet;

public class JobsProvider implements TemplateProvider {
    @Override
    public PlaceholderResolver templatesFor(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        JobsPlayer jobsPlayer = Jobs.getPlayerManager().getJobsPlayer(source.getUniqueId());

        var templates = new HashSet<Placeholder<?>>();

        if (jobsPlayer != null) {
            templates.add(Placeholder.component("job-level", Component.text(jobsPlayer.getTotalLevels())));
            var progression = jobsPlayer.getJobProgression().stream().max(Comparator.comparingInt(JobProgression::getLevel));
            progression.ifPresentOrElse(
                    jobProgression -> templates.add(Placeholder.component("highest-job", Component.text(jobProgression.getJob().getJobFullName()))),
                    () -> templates.add(Placeholder.component("highest-job", Component.empty()))
            );
        } else {
            Placeholder.component("job-level", Component.empty());
            Placeholder.component("highest-job", Component.empty());
        }
        return PlaceholderResolver.placeholders(templates);
    }
}
