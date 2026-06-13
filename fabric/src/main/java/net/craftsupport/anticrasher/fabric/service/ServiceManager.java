package net.craftsupport.anticrasher.fabric.service;

import net.skullian.zenith.core.flavor.Flavor;
import net.skullian.zenith.core.flavor.FlavorOptions;
import lombok.experimental.UtilityClass;
import net.craftsupport.anticrasher.common.manager.CheckManager;
import net.craftsupport.anticrasher.common.util.ACLogger;
import net.craftsupport.anticrasher.fabric.AntiCrasher;
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
                AntiCrasher.instance.getClass(),
                new FlavorOptions(
                        new JavaLogAdapter(logger), // Fabric builtin is slf4j
                        AntiCrasher.instance.getClass().getPackageName()
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
