package org.ognjen.ogiPlayerStats.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.ognjen.ogiPlayerStats.OgiPlayerStats;
import org.ognjen.ogiPlayerStats.utils.StatHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatsExpansion extends PlaceholderExpansion {

    private final OgiPlayerStats plugin;

    public StatsExpansion(OgiPlayerStats plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "ogiplayerstats"; // Placeholder will be %ogiplayerstats_<identifier>%
    }

    @Override
    public @NotNull String getAuthor() {
        return "ogi";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true; // This is required for the expansion to persist across reloads
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String identifier) {
        if (player == null) {
            return "";
        }
        plugin.getDebugLogger().log("PAPI requested identifier: '" + identifier + "'");

        // Combine standard stats and the time stat into one list for checking

        List<ConfigurationSection> standardStats = plugin.getConfig().getMapList("displayed-stats")
                .stream()
                .map(map -> plugin.getConfig().createSection("temp", map))
                .collect(Collectors.toList());
        List<ConfigurationSection> allStatSections = new ArrayList<>(standardStats);

        // Iterate through all configured stats to find a match
        for (ConfigurationSection statConfig : allStatSections) {
            String statName = statConfig.getString("name");
            if (statName == null) continue;


            // Create a placeholder-friendly identifier from the stat name in the config
            // e.g., "&bPlayer Kills" -> "player_kills
            String nameWithoutPlaceholders = statName.replace("{player}", "");

            String strippedName = nameWithoutPlaceholders.replaceAll("(?i)&[0-9A-FK-OR]", "");

            String configIdentifier = strippedName.toLowerCase()
                    .replace(" ", "_")
                    .replace("'", "");

            plugin.getDebugLogger().log("Comparing with config-generated identifier: '" + configIdentifier + "'");

            if (configIdentifier.equals(identifier)) {
                long value = StatHelper.getStatValue(player, statConfig);
                return String.valueOf(value);
            }
        }

        return null; // Let PAPI know this placeholder is not ours
    }
}