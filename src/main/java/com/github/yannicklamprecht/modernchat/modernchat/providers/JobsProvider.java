package com.github.yannicklamprecht.modernchat.modernchat.providers;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobProgression;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.github.yannicklamprecht.modernchat.modernchat.TemplateProvider;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashSet;

public class JobsProvider implements TemplateProvider {

    @Override
    public TagResolver templatesFor(@NotNull Player source, @NotNull Component sourceDisplayName,
                                    @NotNull Component message, @NotNull Audience viewer) {
        JobsPlayer jobsPlayer = Jobs.getPlayerManager().getJobsPlayer(source.getUniqueId());

        var templates = new HashSet<TagResolver>();
        if (jobsPlayer != null) {
            templates.add(
                    Placeholder.component("job-level", Component.text(jobsPlayer.getTotalLevels())));

            var jobProgressions = jobsPlayer.getJobProgression();
            Component jobFullName = Component.empty();
            if (jobProgressions != null) {
                var progression = jobsPlayer.getJobProgression().stream()
                        .max(Comparator.comparingInt(JobProgression::getLevel));
                if (progression.isPresent()) {
                    var job = progression.get().getJob();
                    if (job != null) {
                        jobFullName = Component.text(job.getJobFullName());
                    }
                }
            }
            templates.add(Placeholder.component("highest-job", jobFullName));
        } else {
            templates.add(Placeholder.component("job-level", Component.empty()));
            templates.add(Placeholder.component("highest-job", Component.empty()));
        }
        return TagResolver.resolver(templates);
    }
}
