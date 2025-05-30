package net.craftsupport.anticrasher.fabric;

import info.preva1l.trashcan.Version;
import lombok.Getter;
import lombok.Setter;
import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.Platform;
import net.craftsupport.anticrasher.fabric.api.FabricAntiCrasherAPI;
import net.craftsupport.anticrasher.fabric.library.LibraryLoader;
import net.craftsupport.anticrasher.fabric.service.ServiceManager;
import net.craftsupport.anticrasher.fabric.user.FabricUser;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class AntiCrasher implements DedicatedServerModInitializer, Platform {

    public static AntiCrasher instance;
    public static MinecraftServer server;

    public final Logger logger = LoggerFactory.getLogger("AntiCrasher");
    @Setter
    @Getter
    private FabricUser consoleUser;

    @Override
    public void onInitializeServer() {
        instance = this;

        AntiCrasherAPI.setInstance(new FabricAntiCrasherAPI());

        LibraryLoader libraryLoader = new LibraryLoader();
        libraryLoader.load();

        ServiceManager.onEnable();
    }

    @Override
    public Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir().resolve("anticrasher");
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
    public String getPlatformType() {
        return "mod";
    }
}