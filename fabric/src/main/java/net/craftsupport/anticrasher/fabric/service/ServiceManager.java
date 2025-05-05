package net.craftsupport.anticrasher.fabric.service;

import info.preva1l.trashcan.flavor.Flavor;
import info.preva1l.trashcan.flavor.FlavorOptions;
import lombok.experimental.UtilityClass;
import net.craftsupport.anticrasher.common.util.ACLogger;
import net.craftsupport.anticrasher.fabric.AntiCrasher;

import java.util.logging.Logger;

@UtilityClass
public class ServiceManager {

    private Flavor flavor;

    public void onEnable() {
        flavor = Flavor.create(
                AntiCrasher.instance.getClass(),
                new FlavorOptions(
                        Logger.getLogger("AntiCrasher"), // Fabric builtin is slf4j
                        AntiCrasher.instance.getClass().getPackageName()
                )
        );

        flavor.startup();
    }

    public void onDisable() {
        flavor.close();
        ACLogger.info("Closed all services.");
    }
}
