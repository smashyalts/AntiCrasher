package net.skullian.anticrasher.velocity.alert;

import info.preva1l.trashcan.flavor.annotations.Configure;
import info.preva1l.trashcan.flavor.annotations.Service;
import net.craftsupport.anticrasher.common.config.Config;
import net.craftsupport.anticrasher.common.manager.AlertManager;
import net.craftsupport.anticrasher.common.util.ACLogger;
import net.skullian.anticrasher.velocity.AntiCrasher;

@Service
public class VelocityAlertManager extends AlertManager {
    public static final VelocityAlertManager instance = new VelocityAlertManager(); // make flavor happy

    @Override
    public void dispatchCommand(String command) {
        AntiCrasher.getInstance().server.getCommandManager().executeAsync(
                AntiCrasher.getInstance().server.getConsoleCommandSource(),
                command
        );
    }

    @Configure
    public void initialise() {
        AlertManager.setInstance(this);
        ACLogger.info("Console Alerts: " + Config.i().getLogging().isConsole());
        ACLogger.info("File Alerts: " + Config.i().getLogging().isFile());
        ACLogger.info("Chat Alerts: " + Config.i().getLogging().getChat().isChatAlerts());
    }
}
