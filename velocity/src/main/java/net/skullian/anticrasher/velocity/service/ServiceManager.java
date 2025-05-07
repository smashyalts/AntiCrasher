package net.skullian.anticrasher.velocity.service;

import info.preva1l.trashcan.flavor.Flavor;
import info.preva1l.trashcan.flavor.FlavorOptions;
import info.preva1l.trashcan.logging.ServiceLogFormatter;
import lombok.experimental.UtilityClass;
import net.craftsupport.anticrasher.common.manager.CheckManager;
import net.craftsupport.anticrasher.common.util.ACLogger;
import net.skullian.anticrasher.velocity.AntiCrasher;

import java.util.logging.Logger;

@UtilityClass
public class ServiceManager {

    private Flavor flavor;
    private final Logger logger = Logger.getLogger("AntiCrasher-Services");

    public void onEnable() {
        ACLogger.info("Enabling AntiCrasher...");

        logger.setUseParentHandlers(false);
        logger.addHandler(ServiceLogFormatter.asConsoleHandler(true, "AntiCrasher"));

        flavor = Flavor.create(
                AntiCrasher.getInstance().getClass(),
                new FlavorOptions(
                        Logger.getLogger("AntiCrasher"),
                        AntiCrasher.getInstance().getClass().getPackageName()
                )
        );

        flavor.startup();
        ACLogger.info("AntiCrasher enabled with %s checks enabled.".formatted(CheckManager.getInstance().checks.size()));
    }

    public void onDisable() {
        flavor.close();
        ACLogger.info("Closed all services.");
        ACLogger.info("AntiCrasher has disabled.");
    }
}
