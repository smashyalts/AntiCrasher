package net.skullian.anticrasher.velocity.service;

import net.skullian.zenith.core.flavor.Flavor;
import net.skullian.zenith.core.flavor.FlavorOptions;
import lombok.experimental.UtilityClass;
import net.craftsupport.anticrasher.common.manager.CheckManager;
import net.craftsupport.anticrasher.common.util.ACLogger;
import net.skullian.anticrasher.velocity.AntiCrasher;
import net.skullian.zenith.core.logging.adapters.impl.JavaLogAdapter;

import java.util.logging.Logger;

@UtilityClass
public class ServiceManager {

    private Flavor flavor;
    private final Logger logger = Logger.getLogger("AntiCrasher-Services");

    public void onEnable() {
        ACLogger.info("Enabling AntiCrasher...");

        logger.setUseParentHandlers(false);

        flavor = Flavor.create(
                AntiCrasher.getInstance().getClass(),
                new FlavorOptions(
                        new JavaLogAdapter(logger),
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
