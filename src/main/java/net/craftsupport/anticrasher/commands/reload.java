package net.craftsupport.anticrasher.commands;

import net.craftsupport.anticrasher.AntiCrasher;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class reload implements CommandExecutor {
    private final AntiCrasher plugin;
    public reload(AntiCrasher plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.hasPermission("anticrasher.reload")) {
            plugin.reloadConfig();
            commandSender.sendMessage(ChatColor.GREEN + "AntiCrasher config reloaded!");
        } else {
            commandSender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
        }
        return true;
    }
}
