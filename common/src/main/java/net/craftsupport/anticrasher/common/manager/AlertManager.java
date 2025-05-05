package net.craftsupport.anticrasher.common.manager;

import info.preva1l.trashcan.flavor.annotations.Service;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.event.impl.CheckFlagEvent;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.common.check.CheckViolation;
import net.craftsupport.anticrasher.common.config.Config;
import net.craftsupport.anticrasher.common.util.ACLogger;
import net.craftsupport.anticrasher.common.util.TextUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public abstract class AlertManager {
    @Getter
    @Setter
    private static AlertManager instance;

    private final ExecutorService alertExecutor = Executors.newVirtualThreadPerTaskExecutor();

    public void fail(CheckViolation violation) {
        CheckFlagEvent event = new CheckFlagEvent(
                violation.check(),
                violation.user()
        );
        AntiCrasherAPI.getInstance().getEventBus().emit(event);

        if (event.isCancelled()) return;

        if (Config.i().getLogging().isConsole()) {
            ACLogger.warn(
                    formatMessage("<red><bold><player_name></red></bold> <grey>failed <red><bold><exploit_name></red></bold> <dark_grey>[<check_type>]", violation)
            );
        }

        logToFile(violation);
        sendAlerts(violation);
    }

    private void logToFile(CheckViolation violation) {
        if (!Config.i().getLogging().isFile()) return;

        alertExecutor.submit(() -> {
            File logFile = AntiCrasherAPI.getInstance()
                    .getPlatform()
                    .getConfigDirectory()
                    .resolve("logs/violations.log")
                    .toFile();

            logFile.getParentFile().mkdirs();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                String logMessage = String.format("%s - %s failed Check %s [%s].",
                        timestamp,
                        violation.user().getName(),
                        violation.check().getName(),
                        violation.check().getType());
                writer.write(logMessage);
                writer.newLine();
            } catch (IOException e) {
                ACLogger.fatal("Failed to log check violation to file.", e);
            }
        });
    }

    private void sendAlerts(CheckViolation violation) {
        if (!Config.i().getLogging().getChat().isChatAlerts()) return;

        Component hoverText = Config.i().getLogging().getChat().getAlertsHoverFormat().stream()
                .map(each -> TextUtil.text(formatMessage(each, violation)))
                .reduce(Component.empty(), Component::append);
        Component formattedMessage = TextUtil.text(formatMessage(Config.i().getLogging().getChat().getAlertsFormat(), violation))
                .hoverEvent(HoverEvent.showText(hoverText));

        formattedMessage = formattedMessage.hoverEvent(HoverEvent.showText(hoverText));
        for (User user : AntiCrasherAPI.getInstance().getUserManager().getOnlineUsers()) {
            if (user.hasPermission("anticrasher.alerts")) {
                user.sendMessage(formattedMessage);
            }
        }
    }

    private void punish(CheckViolation violation) {
        for (String command : Config.i().getPunishments()) {
            dispatchCommand(violation.user().processPlaceholders(command));
        }

        violation.user().toPE().closeConnection();
    }

    public abstract void dispatchCommand(String command);

    private String formatMessage(String message, CheckViolation violation) {
        return violation.user().processPlaceholders(message
                .replace("<player_name>", violation.user().getName())
                .replace("<exploit_name>", violation.check().getName())
                .replace("<exploit_description>", violation.check().getDescription()));
    }
}
