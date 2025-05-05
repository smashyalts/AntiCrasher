package net.craftsupport.anticrasher.fabric;

import info.preva1l.trashcan.Version;
import lombok.Getter;
import lombok.Setter;
import net.craftsupport.anticrasher.api.Platform;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.fabric.library.LibraryLoader;
import net.craftsupport.anticrasher.fabric.listener.LifecycleListener;
import net.craftsupport.anticrasher.fabric.service.ServiceManager;
import net.craftsupport.anticrasher.fabric.user.FabricUser;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class AntiCrasher implements DedicatedServerModInitializer, Platform {

    public static AntiCrasher instance;
    public static MinecraftServer server;

    public final Logger logger = LoggerFactory.getLogger("AntiCrasher");
    private final LibraryLoader libraryLoader = new LibraryLoader();
    private FabricUser consoleUser;

    @Override
    public void onInitializeServer() {
        instance = this;

        libraryLoader.load();
        ServiceManager.onEnable();

        this.consoleUser = new FabricUser(UUID.randomUUID(), server.getCommandSource());
    }

    @Override
    public Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir().resolve("AntiCrasher");
    }

    @Override
    public boolean isPluginEnabled(String pluginName) {
        return FabricLoader.getInstance().isModLoaded(pluginName);
    }

    @Override
    public Version getCurrentVersion() {
        return Version.fromString(
                FabricLoader.getInstance()
                        .getModContainer("anticrasher")
                        .orElseThrow(() -> new IllegalStateException("Mod 'anticrasher' not found")) // this should NEVER happen
                        .getMetadata()
                        .getVersion()
                        .getFriendlyString()
        );
    }

    @Override
    public void runLater(Runnable runnable, long delay) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(delay);
                server.executeSync(runnable);
            } catch (InterruptedException e) {
                logger.error("Task execution interrupted", e);
                Thread.currentThread().interrupt();
            }
        });
    }

    @Override
    public User getConsoleUser() {
        return consoleUser;
    }
}