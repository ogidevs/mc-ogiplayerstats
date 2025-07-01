package org.ognjen.ogiPlayerStats.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.ognjen.ogiPlayerStats.OgiPlayerStats;

public class JoinListener implements Listener {

    private final OgiPlayerStats plugin;

    public JoinListener(OgiPlayerStats plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) {
            // Let the version checker handle the notification
            plugin.getVersionChecker().notifyPlayer(player);
        }
    }
}