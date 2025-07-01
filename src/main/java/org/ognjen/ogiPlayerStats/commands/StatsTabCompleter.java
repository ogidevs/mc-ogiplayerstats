package org.ognjen.ogiPlayerStats.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatsTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();

            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }

            if (sender.hasPermission("ogiplayerstats.reload")) {
                completions.add("reload");
            }

            return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
        }

        return Collections.emptyList();
    }
}