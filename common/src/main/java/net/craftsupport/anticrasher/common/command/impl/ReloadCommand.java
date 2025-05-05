package net.craftsupport.anticrasher.common.command.impl;

import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.common.config.Config;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.Permission;

@Command("anticrasher|ac")
public class ReloadCommand {

    @Command("reload")
    @Permission(value = "anticrasher.reload")
    public void execute(User user) {
        if (!user.hasPermission("anticrasher.reload")) {
            user.sendMessage("<red>You do not have permission to use this command.");
            return;
        }

        Config.reload();
        user.sendMessage("<green>AntiCrasher configuration reloaded.");
    }
}
