package net.craftsupport.anticrasher.paper.alert;

import com.github.puregero.multilib.MultiLib;
import info.preva1l.trashcan.flavor.annotations.Configure;
import info.preva1l.trashcan.flavor.annotations.Service;
import net.craftsupport.anticrasher.common.config.Config;
import net.craftsupport.anticrasher.common.manager.AlertManager;
import net.craftsupport.anticrasher.common.util.ACLogger;
import net.craftsupport.anticrasher.paper.AntiCrasher;
import org.bukkit.Bukkit;

@Service
public class PaperAlertManager extends AlertManager {
    public static final PaperAlertManager instance = new PaperAlertManager(); // make flavor happy

    @Override
    public void dispatchCommand(String command) {
        MultiLib.getGlobalRegionScheduler().run(AntiCrasher.getInstance(), (task) -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        });
    }

    @Configure
    public void initialise() {
        AlertManager.setInstance(this);
        ACLogger.info("Console Alerts: " + Config.i().getLogging().isConsole());
        ACLogger.info("File Alerts: " + Config.i().getLogging().isFile());
        ACLogger.info("Chat Alerts: " + Config.i().getLogging().getChat().isChatAlerts());
    }
}
