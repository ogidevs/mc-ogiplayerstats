// OgiPlayerStats/src/main/java/org/ognjen/ogiPlayerStats/commands/StatsCommand.java

package org.ognjen.ogiPlayerStats.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.ognjen.ogiPlayerStats.OgiPlayerStats;

public class StatsCommand implements CommandExecutor {

    private final OgiPlayerStats plugin;

    public StatsCommand(OgiPlayerStats plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // --- Handle Reload Command ---
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("ogiplayerstats.reload")) {
                sendMessage(sender, plugin.getConfig().getString("messages.no-permission"));
                return true;
            }
            plugin.reloadConfig();
            sendMessage(sender, "&aOgiPlayerStats configuration has been reloaded.");
            return true;
        }
        // -------------------------

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player viewer = (Player) sender;
        OfflinePlayer target;

        if (args.length == 0) {
            if (!viewer.hasPermission("ogiplayerstats.view")) {
                sendMessage(viewer, plugin.getConfig().getString("messages.no-permission"));
                return true;
            }
            target = viewer;
        } else if (args.length == 1) {
            if (!viewer.hasPermission("ogiplayerstats.view.others")) {
                sendMessage(viewer, plugin.getConfig().getString("messages.no-permission"));
                return true;
            }
            target = Bukkit.getOfflinePlayer(args[0]);

            if (!target.hasPlayedBefore() && !target.isOnline()) {
                String notFoundMsg = plugin.getConfig().getString("messages.player-not-found", "&cPlayer '{player}' not found.");
                sendMessage(viewer, notFoundMsg.replace("{player}", args[0]));
                return true;
            }
        } else {
            viewer.sendMessage(ChatColor.RED + "Usage: " + command.getUsage());
            return true;
        }

        plugin.getGuiManager().openStatsGui(viewer, target);

        return true;
    }

    private void sendMessage(CommandSender sender, String message) {
        if (message != null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }
}