package org.ognjen.ogiPlayerStats.utils;

import org.ognjen.ogiPlayerStats.OgiPlayerStats;

public class DebugLogger {

    private final OgiPlayerStats plugin;

    public DebugLogger(OgiPlayerStats plugin) {
        this.plugin = plugin;
    }

    public void log(String message) {
        if (plugin.getConfig().getBoolean("settings.debug-mode", false)) {
            plugin.getLogger().info("[DEBUG] " + message);
        }
    }
}