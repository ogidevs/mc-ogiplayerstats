// OgiPlayerStats/src/main/java/org/ognjen/ogiPlayerStats/utils/StatHelper.java

package org.ognjen.ogiPlayerStats.utils;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.ognjen.ogiPlayerStats.OgiPlayerStats;

public class StatHelper {

    /**
     * Calculates a specific statistic for a player based on a configuration section.
     * This handles simple, typed (block/entity).
     *
     * @param player The player whose stat to get.
     * @param statConfig The ConfigurationSection for the stat from config.yml.
     * @return The calculated value of the statistic.
     */
    public static long getStatValue(OfflinePlayer player, ConfigurationSection statConfig) {
        if (player == null || statConfig == null) {
            return 0;
        }

        try {
            Statistic statistic = Statistic.valueOf(statConfig.getString("statistic", "").toUpperCase());
            // Handle block or entity-based stats
            String type = statConfig.getString("type");
            if (type != null) {
                String subType = statConfig.getString("sub-type");
                if (subType == null) {
                    OgiPlayerStats.getInstance().getLogger().warning("Stat '" + statConfig.getString("name") + "' is missing a 'sub-type'!");
                    return 0;
                }

                if (type.equalsIgnoreCase("ENTITY_TYPE")) {
                    // Handle entity-based stats like KILL_ENTITY
                    return player.getStatistic(statistic, EntityType.valueOf(subType.toUpperCase()));
                } else if (type.equalsIgnoreCase("BLOCK") || type.equalsIgnoreCase("ITEM")) {
                    // Handle both BLOCK and ITEM stats, which both use the Material enum
                    return player.getStatistic(statistic, Material.valueOf(subType.toUpperCase()));
                }
            }

            // Handle simple stats
            return player.getStatistic(statistic);

        } catch (Exception e) {
            // Log an error if the statistic in the config is invalid
            OgiPlayerStats.getInstance().getDebugLogger().log("Failed to parse stat: " + e.getMessage());
            return 0;
        }
    }
}