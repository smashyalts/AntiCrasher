package net.craftsupport.anticrasher.common.command.impl;

import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.common.config.Config;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.Permission;

@Command("ac")
public class ReloadCommand {

    @Command("reload")
    @Permission(value = "anticrasher.command.reload")
    public void execute(User user) {
        if (!user.hasPermission("anticrasher.command.reload")) { // sanity check
            user.sendMessage("<blue><bold>AntiCrasher<reset> <dark_grey>» <red>You do not have permission to use this command.");
            return;
        }

        Config.reload();
        user.sendMessage("<blue><bold>AntiCrasher<reset> <dark_grey>» <green>AntiCrasher configuration reloaded.");
    }
}
