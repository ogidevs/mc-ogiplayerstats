package org.ognjen.ogiPlayerStats.inventory;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.ognjen.ogiPlayerStats.OgiPlayerStats;
import org.ognjen.ogiPlayerStats.utils.StatHelper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GuiManager {

    private final OgiPlayerStats plugin;
    private final boolean papiEnabled;

    public GuiManager(OgiPlayerStats plugin) {
        this.plugin = plugin;
        this.papiEnabled = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }


    public void openStatsGui(Player viewer, OfflinePlayer target) {
        ConfigurationSection guiSettings = plugin.getConfig().getConfigurationSection("gui-settings");
        if (guiSettings == null) {
            viewer.sendMessage(ChatColor.RED + "GUI settings are not configured properly.");
            return;
        }

        String title = ChatColor.translateAlternateColorCodes('&',
                guiSettings.getString("title", "&1Stats for {player}")
                        .replace("{player}", target.getName() != null ? target.getName() : "Unknown"));
        int size = guiSettings.getInt("size", 36);

        Inventory gui = Bukkit.createInventory(new StatsGuiHolder(), size, title);

        // Fill empty slots if enabled
        if (guiSettings.getBoolean("filler-item.enabled", false)) {
            try {
                Material fillerMaterial = Material.valueOf(guiSettings.getString("filler-item.material", "GRAY_STAINED_GLASS_PANE"));
                ItemStack fillerItem = new ItemStack(fillerMaterial);
                ItemMeta fillerMeta = fillerItem.getItemMeta();
                if (fillerMeta != null) {
                    fillerMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', guiSettings.getString("filler-item.name", " ")));
                    fillerItem.setItemMeta(fillerMeta);
                }

                for (int i = 0; i < size; i++) {
                    gui.setItem(i, fillerItem);
                }
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid filler material in config.yml: " + guiSettings.getString("filler-item.material"));
            }
        }

        // Populate standard stats
        populateStats(gui, target);

        viewer.openInventory(gui);
    }

    private void populateStats(Inventory gui, OfflinePlayer target) {
        List<ConfigurationSection> statSections = plugin.getConfig().getMapList("displayed-stats")
                .stream()
                .map(map -> plugin.getConfig().createSection("temp", map))
                .collect(Collectors.toList());

        ConfigurationSection timeSection = plugin.getConfig().getConfigurationSection("time-stat");
        if (timeSection != null && timeSection.getBoolean("enabled")) {
            statSections.add(timeSection);
        }

        for (ConfigurationSection section : statSections) {
            try {
                int slot = section.getInt("slot");
                Material material = Material.valueOf(section.getString("material", "STONE").toUpperCase());
                String name = section.getString("name", "&cUnnamed Stat"); // <-- Get raw name first
                List<String> lore = section.getStringList("lore");
                String value;

                if (section.isString("placeholder")) {
                    if (papiEnabled) {
                        String placeholder = section.getString("placeholder");
                        assert placeholder != null;
                        value = PlaceholderAPI.setPlaceholders(target.getPlayer(), placeholder);
                    } else {
                        value = "&cPlaceholderAPI not found!";
                    }
                } else {
                    value = String.valueOf(StatHelper.getStatValue(target, section));
                }

                ItemStack statItem = new ItemStack(material);
                ItemMeta meta = statItem.getItemMeta();

                if (meta != null) {
                    String finalName = name.replace("{player}", target.getName() != null ? target.getName() : "Unknown");
                    meta.setDisplayName(colorize(finalName)); // <-- Use the processed name

                    final String finalValue = value;
                    List<String> finalLore = lore.stream()
                            .map(line -> colorize(line.replace("{value}", finalValue)))
                            .collect(Collectors.toList());
                    meta.setLore(finalLore);

                    if (meta instanceof SkullMeta && section.isString("skull-owner")) {
                        SkullMeta skullMeta = (SkullMeta) meta;
                        String ownerName = Objects.requireNonNull(section.getString("skull-owner")).replace("{player}", Objects.requireNonNull(target.getName()));
                        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(ownerName));
                    }

                    statItem.setItemMeta(meta);
                }
                gui.setItem(slot, statItem);

            } catch (Exception e) {
                plugin.getLogger().warning("Could not load a stat item from config.yml. Please check for errors in section: " + section.getCurrentPath());
                e.printStackTrace();
            }
        }
    }

    private String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}