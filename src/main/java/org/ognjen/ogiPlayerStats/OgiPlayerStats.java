// OgiPlayerStats/src/main/java/org/ognjen/ogiPlayerStats/OgiPlayerStats.java

package org.ognjen.ogiPlayerStats;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.ognjen.ogiPlayerStats.commands.StatsCommand;
import org.ognjen.ogiPlayerStats.commands.StatsTabCompleter;
import org.ognjen.ogiPlayerStats.inventory.GuiManager;
import org.ognjen.ogiPlayerStats.listeners.GuiListener;
import org.ognjen.ogiPlayerStats.listeners.JoinListener;
import org.ognjen.ogiPlayerStats.papi.StatsExpansion;
import org.ognjen.ogiPlayerStats.utils.DebugLogger;
import org.ognjen.ogiPlayerStats.utils.VersionChecker;

public final class OgiPlayerStats extends JavaPlugin {

    private static OgiPlayerStats instance;
    private GuiManager guiManager;
    private DebugLogger debugLogger;
    private VersionChecker versionChecker;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        this.debugLogger = new DebugLogger(this);
        this.versionChecker = new VersionChecker(this);

        this.guiManager = new GuiManager(this);

        getCommand("stats").setExecutor(new StatsCommand(this));
        getCommand("stats").setTabCompleter(new StatsTabCompleter());

        getServer().getPluginManager().registerEvents(new GuiListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);

        // --- PlaceholderAPI Hook ---
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new StatsExpansion(this).register();
            getLogger().info("Successfully hooked into PlaceholderAPI!");
        } else {
            getLogger().info("PlaceholderAPI not found, placeholders will not work.");
        }
        // -------------------------

        getLogger().info("OgiPlayerStats has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("OgiPlayerStats has been disabled!");
    }

    public static OgiPlayerStats getInstance() {
        return instance;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public DebugLogger getDebugLogger() {
        return debugLogger;
    }

    public VersionChecker getVersionChecker() {
        return versionChecker;
    }
}