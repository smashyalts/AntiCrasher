package net.craftsupport.anticrasher.common.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ACLogger {

    private static final ComponentLogger logger = ComponentLogger.logger("AntiCrasher");
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static void info(String message, Object... args) {
        Component component = miniMessage.deserialize("<#4294ed>" + message + "</#4294ed><reset>");
        logger.info(component, args);
    }

    public static void warn(String message, Object... args) {
        Component component = miniMessage.deserialize("<#f28f24>$message</#f28f24><reset>");
        logger.warn(component, args);
    }

    public static void fatal(String message, Object... args) {
        logger.error(message, args);
    }
}
