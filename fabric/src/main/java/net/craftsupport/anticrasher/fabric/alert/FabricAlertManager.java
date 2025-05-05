package net.craftsupport.anticrasher.fabric.alert;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import info.preva1l.trashcan.flavor.annotations.Configure;
import info.preva1l.trashcan.flavor.annotations.Service;
import net.craftsupport.anticrasher.common.config.Config;
import net.craftsupport.anticrasher.common.manager.AlertManager;
import net.craftsupport.anticrasher.common.util.ACLogger;
import net.craftsupport.anticrasher.fabric.AntiCrasher;
import net.minecraft.server.command.ServerCommandSource;

@Service
public class FabricAlertManager extends AlertManager {
    public static final FabricAlertManager instance = new FabricAlertManager(); // make flavor happy

    @Override
    public void dispatchCommand(String command) {
        try {
            //? if >1.21 {
            CommandDispatcher<ServerCommandSource> dispatcher = AntiCrasher.server.getCommandSource().getDispatcher();
            //?} elif <1.21 {
            //CommandDispatcher<ServerCommandSource> dispatcher = AntiCrasher.server.getCommandManager().getDispatcher();
            //?}
            ParseResults<ServerCommandSource> parseResults = dispatcher.parse(command, AntiCrasher.server.getCommandSource());

            dispatcher.execute(parseResults);
        } catch (CommandSyntaxException error) {
            throw new RuntimeException("Failed to dispatch command: " + command, error);
        }
    }

    @Configure
    public void initialise() {
        AlertManager.setInstance(this);
        ACLogger.info("Console Alerts: " + Config.i().getLogging().isConsole());
        ACLogger.info("File Alerts: " + Config.i().getLogging().isFile());
        ACLogger.info("Chat Alerts: " + Config.i().getLogging().getChat().isChatAlerts());
    }
}
