package net.craftsupport.anticrasher.commands;

import net.craftsupport.anticrasher.AntiCrasher;
import net.craftsupport.anticrasher.utils.utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class reload implements CommandExecutor {
    private final AntiCrasher plugin;
    private final utils utilsInstance;
    public reload(AntiCrasher plugin) {
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
