package org.ognjen.ogiPlayerStats.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.ognjen.ogiPlayerStats.inventory.StatsGuiHolder;

public class GuiListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Check if the inventory clicked is our custom stats GUI
        if (event.getInventory().getHolder() instanceof StatsGuiHolder) {
            // Cancel the event to prevent players from taking items
            event.setCancelled(true);
        }
    }
}