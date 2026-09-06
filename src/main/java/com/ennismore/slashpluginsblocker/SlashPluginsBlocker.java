package com.ennismore.slashpluginsblocker;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public final class SlashPluginsBlocker extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(this, this);

        getLogger().info("SlashPluginsBlocker enabled.");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (!getConfig().getBoolean("blocking-enabled", true)) {
            return;
        }

        String command = event.getMessage()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (command.equals("/plugins")
                || command.equals("/pl")
                || command.equals("/bukkit:plugins")
                || command.equals("/bukkit:pl")) {

            event.setCancelled(true);

            event.getPlayer().sendMessage(
                    ChatColor.RED + "(SlashPluginsBlocker) This command is blocked!"
            );
        }
    }
}
