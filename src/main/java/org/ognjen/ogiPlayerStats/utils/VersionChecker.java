package org.ognjen.ogiPlayerStats.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.ognjen.ogiPlayerStats.OgiPlayerStats;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VersionChecker {

    private final OgiPlayerStats plugin;
    private final String currentVersion;
    private String latestVersion;
    private boolean newVersionAvailable = false;

    public VersionChecker(OgiPlayerStats plugin) {
        this.plugin = plugin;
        this.currentVersion = plugin.getDescription().getVersion();
    }

    public void check() {
        if (!plugin.getConfig().getBoolean("settings.version-checker", true)) {
            return;
        }

        // Run this asynchronously to avoid freezing the server
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URL url = new URL("https://api.github.com/repos/ogidevs/mc-ogiplayerstats/releases/latest");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                if (connection.getResponseCode() == 200) {
                    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                    JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                    latestVersion = json.get("tag_name").getAsString();

                    // Remove 'v' prefix if it exists (e.g., v1.0.1 -> 1.0.1)
                    if (latestVersion.startsWith("v")) {
                        latestVersion = latestVersion.substring(1);
                    }

                    if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                        newVersionAvailable = true;
                        plugin.getLogger().info("A new version of OgiPlayerStats is available: " + latestVersion);
                        plugin.getLogger().info("Download it from: https://github.com/ogidevs/mc-ogiplayerstats/releases/latest");
                    } else {
                        plugin.getLogger().info("You are running the latest version of OgiPlayerStats.");
                    }
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Could not check for new updates: " + e.getMessage());
            }
        });
    }

    public void notifyPlayer(Player player) {
        if (newVersionAvailable) {
            // Send message back on the main server thread
            Bukkit.getScheduler().runTask(plugin, () -> {
                player.sendMessage(ChatColor.GOLD + "[OgiPlayerStats] " + ChatColor.GREEN + "A new version (" + latestVersion + ") is available!");
                player.sendMessage(ChatColor.GRAY + "Download it from GitHub or your plugin platform.");
            });
        }
    }
}