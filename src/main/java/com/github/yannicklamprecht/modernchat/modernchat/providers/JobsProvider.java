package com.github.yannicklamprecht.modernchat.modernchat.providers;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.JobProgression;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.github.yannicklamprecht.modernchat.modernchat.TemplateProvider;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Template;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class JobsProvider implements TemplateProvider {
    @Override
    public Set<Template> templatesFor(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        JobsPlayer jobsPlayer = Jobs.getPlayerManager().getJobsPlayer(source.getUniqueId());

        var templates = new HashSet<Template>();
        templates.add(Template.of("job-level", Component.text(jobsPlayer.getTotalLevels())));

        jobsPlayer.getJobProgression().stream().max(Comparator.comparingInt(JobProgression::getLevel)).ifPresent(jobProgression -> {
            templates.add(Template.of("highest-job", jobProgression.getJob().getJobFullName()));
        });
        return templates;
    }
}
