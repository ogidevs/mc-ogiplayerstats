package org.ognjen.ogiPlayerStats.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class StatsGuiHolder implements InventoryHolder {
    // This class is intentionally empty.
    // It's used as a "marker" to identify our custom GUIs.

    @Override
    public @NotNull Inventory getInventory() {
        // This method is required by the interface but doesn't need to do anything
        // for this implementation, as we create a new inventory each time.
        return null;
    }
}