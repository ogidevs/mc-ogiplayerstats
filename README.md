<p align="center">
  <img src="https://raw.githubusercontent.com/ogidevs/mc-ogiplayerstats/main/logo.png" alt="OgiPlayerStats Banner" width="200"/>
</p>

<h1 align="center">OgiPlayerStats</h1>

<p align="center">
  <strong>A modern, powerful, and 100% free player statistics GUI for Minecraft servers.</strong>
  <br>
  Built with performance and versatility in mind.
</p>


<p align="center">
  <!-- GitHub Badges -->
  <a href="#"><img src="https://img.shields.io/static/v1?label=Minecraft&message=1.16.5%20-%201.21&color=brightgreen" alt="Supported Versions"></a>
  <a href="https://github.com/ogidevs/mc-ogiplayerstats/issues"><img src="https://img.shields.io/github/issues/ogidevs/mc-ogiplayerstats" alt="GitHub issues"></a>
  <a href="https://github.com/ogidevs/mc-ogiplayerstats/blob/main/LICENSE"><img src="https://img.shields.io/github/license/ogidevs/mc-ogiplayerstats?cacheSeconds=1" alt="License"></a>
</p>

---

## 📖 Table of Contents

- [A Note From The Developer](#-a-note-from-the-developer)
- [About the Plugin](#-about-the-plugin)
- [✨ Key Features](#-key-features)
- [🚀 Installation](#-installation)
- [⚙️ Configuration](#️-configuration)
    - [Adding a Built-in Stat](#adding-a-built-in-stat)
    - [Adding a PlaceholderAPI Stat](#adding-a-placeholderapi-stat)
- [📝 Commands & Permissions](#-commands--permissions)
- [🧩 PlaceholderAPI Integration](#-placeholderapi-integration)
- [🤝 Contributing](#-contributing)
- [🐛 Bug Reports](#-bug-reports)

---

### 💬 A Note From The Developer
I was tired of seeing essential, high-quality server plugins locked behind a paywall. I believe powerful tools should be accessible to everyone in the Minecraft community. That's why I created **OgiPlayerStats**—to provide a premium, feature-rich experience for free. I hope you enjoy it!

---

## 🌟 About the Plugin

**OgiPlayerStats** provides a beautiful and intuitive in-game menu to display player statistics. Unlike other stat plugins, it's built from the ground up to be 100% configurable via a simple `config.yml` file. You can display anything from vanilla Minecraft stats (`PLAYER_KILLS`, `DIAMONDS_MINED`) to custom data from other plugins like Vault or EssentialsX using the powerful PlaceholderAPI integration.

<img src="https://raw.githubusercontent.com/ogidevs/mc-ogiplayerstats/main/showcase/img1.png" alt="OgiPlayerStats Banner" width="200"/>

*An example of the default stats GUI.*

## ✨ Key Features

-   **Completely Free & Open Source**: No premium versions, no hidden costs. All features are available to everyone.
-   **Fully Configurable GUI**: Control the title, size, items, names, lore, and slot for every stat.
-   **Extensive Stat Support**: Track any vanilla statistic, including those with subtypes (Blocks, Entities).
-   **Powerful PlaceholderAPI Integration**:
    -   **Import**: Display placeholders from *any* other PAPI-enabled plugin.
    -   **Export**: Creates dynamic placeholders for every stat you configure (e.g., `%ogiplayerstats_player_kills%`).
-   **Dynamic Item Skins**: Use `PLAYER_HEAD` material with a `skull-owner` to display player-specific skins.
-   **Optimized Performance**: Lightweight and designed to ensure minimal server impact.
-   **Universal Compatibility**: A single JAR file supports all server versions from **1.16.5 to 1.21+**.

## 🚀 Installation

1.  Download the latest release from the [Releases Page](https://github.com/ogidevs/mc-ogiplayerstats/releases/latest), [SpigotMC](YOUR_SPIGOT_LINK_HERE), or [Modrinth](YOUR_MODRINTH_LINK_HERE).
2.  Place the `OgiPlayerStats-X.X.X.jar` file into your server's `/plugins` directory.
3.  **(Optional but Recommended)** Install [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) to unlock the full potential of the plugin.
4.  Restart your server. The default configuration file will be generated at `/plugins/OgiPlayerStats/config.yml`.

## ⚙️ Configuration

All configuration is handled in the `config.yml` file. You can add, remove, or modify any stat in the `displayed-stats` list.


## 🚀 Installation

1.  Download the latest release from the [Releases Page](https://github.com/ogidevs/mc-ogiplayerstats/releases/latest), [SpigotMC](YOUR_SPIGOT_LINK_HERE), or [Modrinth](YOUR_MODRINTH_LINK_HERE).
2.  Place the `OgiPlayerStats-X.X.X.jar` file into your server's `/plugins` directory.
3.  **(Optional but Recommended)** Install [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) to unlock the full potential of the plugin.
4.  Restart your server. The default configuration file will be generated at `/plugins/OgiPlayerStats/config.yml`.

## ⚙️ Configuration

All configuration is handled in the `config.yml` file. You can add, remove, or modify any stat in the `displayed-stats` list.

### Adding a Built-in Stat

To display a statistic native to Minecraft, use the `statistic` key. For stats that need a subtype (like breaking a specific block), also include `type` and `sub-type`.

```yaml
- slot: 14
  material: "DIAMOND_PICKAXE"
  name: "&bDiamonds Mined"
  lore:
    - "&7Diamond ore blocks broken."
    - ""
    - "&aValue: &f{value}"
  statistic: "MINE_BLOCK"
  type: "BLOCK"
  sub-type: "DIAMOND_ORE"
```

### Adding a PlaceholderAPI Stat

To display a value from another plugin, simply use the `placeholder` key instead of `statistic`. The `{value}` in the lore will be replaced by the placeholder's output.

```yaml
- slot: 13
  material: "GOLD_INGOT"
  name: "&ePlayer Balance"
  lore:
    - "&7Your current balance."
    - ""
    - "&aValue: &f{value}"
  placeholder: "%vault_eco_balance_formatted%"
```

To display a statistic native to Minecraft, use the `statistic` key. For stats that need a subtype (like breaking a specific block), also include `type` and `sub-type`.

> For a full list of available statistics and material names, please refer to the [Spigot Javadocs](https://hub.spigotmc.org/javadocs/spigot/).

## 📝 Commands & Permissions

| Command              | Permission                     | Description                                  |
| -------------------- | ------------------------------ | -------------------------------------------- |
| `/stats [player]`    | `ogiplayerstats.view` / `ogiplayerstats.view.others` | View your own or another player's statistics.  |
| `/stats reload`      | `ogiplayerstats.reload`        | Reloads the plugin's configuration file.     |

## 🧩 PlaceholderAPI Integration

**OgiPlayerStats** automatically generates its own placeholders based on the `name` field of each stat in your configuration. The name is converted to lowercase, stripped of color codes, and spaces are replaced with underscores.

**Example:**
A stat with `name: "&bPlayer Kills"` becomes `%ogiplayerstats_player_kills%`.
A stat with `name: "&ePlayer Balance"` becomes `%ogiplayerstats_player_balance%`.

You can use these placeholders in any plugin that supports PlaceholderAPI, such as scoreboards, chat formatters, or holograms.

## 🤝 Contributing

Contributions are welcome! If you'd like to contribute to the project, please follow these steps:

1.  Fork the repository.
2.  Create a new branch (`git checkout -b feature/YourAmazingFeature`).
3.  Make your changes.
4.  Commit your changes (`git commit -m 'Add some amazing feature'`).
5.  Push to the branch (`git push origin feature/YourAmazingFeature`).
6.  Open a new Pull Request.

## 🐛 Bug Reports

If you find a bug, please open an issue on the [GitHub Issues](https://github.com/ogidevs/mc-ogiplayerstats/issues) page.

Please include the following information in your report:
-   Server version (e.g., Paper 1.21.5)
-   Plugin version
-   A detailed description of the bug
-   Steps to reproduce the bug
-   Any relevant errors from your console log

---
<p align="center">
  Developed by Ogi.
</p>
