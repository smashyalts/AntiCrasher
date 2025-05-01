package net.craftsupport.anticrasher.commands;

import net.craftsupport.anticrasher.AntiCrasher;
import net.craftsupport.anticrasher.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final AntiCrasher plugin;
    public Utils util;

    public ReloadCommand(AntiCrasher plugin, Utils util) {
        this.plugin = plugin;
        this.util = util;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (command.getName().equalsIgnoreCase("acreload")) {
            if (!commandSender.hasPermission("anticrasher.reload")) {
                commandSender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }

            commandSender.sendMessage(ChatColor.GREEN + "Plugin successfully reloaded!");
            plugin.reloadConfig();
            util.reloadConfig();
        }
        return true;
    }
}
