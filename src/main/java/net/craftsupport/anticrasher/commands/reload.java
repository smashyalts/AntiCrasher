package net.craftsupport.anticrasher.commands;

import net.craftsupport.anticrasher.AntiCrasher;
import net.craftsupport.anticrasher.utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class reload implements CommandExecutor {
    private final AntiCrasher plugin;
    private final utils utilsInstance;
    public reload(AntiCrasher plugin) {
        this.plugin = plugin;
        this.utilsInstance = new utils(plugin);

    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.hasPermission("anticrasher.reload")) {
            AntiCrasher.getPlugin(AntiCrasher.class).reloadConfig();
            utilsInstance.logattempts = plugin.getConfig().getBoolean("log-attempts");
            utilsInstance.logtofile = plugin.getConfig().getBoolean("log-to-file");
            utilsInstance.punishonattempt = plugin.getConfig().getBoolean("punish-on-attempt");
            utilsInstance.punishcommand = plugin.getConfig().getString("punish-command");
            commandSender.sendMessage(ChatColor.GREEN + "AntiCrasher config reloaded!");
        } else {
            commandSender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
        }
        return true;
    }
}
