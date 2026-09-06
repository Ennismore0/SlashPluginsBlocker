package com.ennismore.slashpluginsblocker;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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

        if (event.getPlayer().isOp()
                && !getConfig().getBoolean("enable-op-blocking", true)) {
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

            String message = getConfig().getString(
                    "customize-block-message",
                    "&c(SlashPluginsBlocker) This command is blocked!"
            );

            event.getPlayer().sendMessage(
                    ChatColor.translateAlternateColorCodes('&', message)
            );
        }
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (!command.getName().equalsIgnoreCase("slashpluginsblocker")) {
            return false;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            reloadConfig();

            sender.sendMessage(
                    ChatColor.GREEN + "SlashPluginsBlocker configuration reloaded!"
            );

            return true;
        }

        sender.sendMessage(
                ChatColor.RED + "Usage: /" + label + " reload"
        );

        return true;
    }
}
