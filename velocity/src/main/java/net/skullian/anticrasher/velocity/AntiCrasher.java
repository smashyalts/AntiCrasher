package net.skullian.anticrasher.velocity;

import com.github.retrooper.packetevents.PacketEvents;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import info.preva1l.trashcan.Version;
import io.github.retrooper.packetevents.velocity.factory.VelocityPacketEventsBuilder;
import lombok.Getter;
import lombok.Setter;
import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.Platform;
import net.craftsupport.anticrasher.velocity.BuildConstants;
import net.skullian.anticrasher.velocity.api.VelocityAntiCrasherAPI;
import net.skullian.anticrasher.velocity.library.LibraryLoader;
import net.skullian.anticrasher.velocity.service.ServiceManager;
import net.skullian.anticrasher.velocity.user.VelocityUser;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Plugin(
        id = "anticrasher",
        name = "AntiCrasher",
        version = BuildConstants.VERSION,
        authors = {
                "CraftSupport",
                "RivenBytes",
                "Skullians",
                "RebelMythik",
                "MachineBreaker"
        },
        url = "https://modrinth.com/plugin/anticrasher",
        dependencies = {
                @Dependency(id = "viaversion", optional = true),
                @Dependency(id = "viabackwards", optional = true),
                @Dependency(id = "viarewind", optional = true),
                @Dependency(id = "geyser", optional = true)
        }
)
public class AntiCrasher implements Platform {
    @Getter private static AntiCrasher instance;

    public final ProxyServer server;
    public final Logger logger;
    @Getter private final PluginContainer pluginContainer;
    private final Path dataDirectory;

    @Setter
    @Getter
    private VelocityUser consoleUser;

    @Inject
    public AntiCrasher(ProxyServer server, Logger logger, PluginContainer pluginContainer, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.pluginContainer = pluginContainer;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        instance = this;

        PacketEvents.setAPI(VelocityPacketEventsBuilder.build(server, pluginContainer, logger, dataDirectory));
        PacketEvents.getAPI().getSettings().reEncodeByDefault(false)
                .checkForUpdates(true)
                .bStats(true)
                .kickOnPacketException(true);
        PacketEvents.getAPI().load();
        PacketEvents.getAPI().init();

        AntiCrasherAPI.setInstance(new VelocityAntiCrasherAPI());

        LibraryLoader libraryLoader = new LibraryLoader();
        libraryLoader.load();

        AntiCrasher.instance.setConsoleUser(new VelocityUser(null, UUID.randomUUID(), server.getConsoleCommandSource()));

        ServiceManager.onEnable();
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        ServiceManager.onDisable();
        PacketEvents.getAPI().terminate();
    }

    @Override
    public Path getConfigDirectory() {
        return dataDirectory;
    }

    @Override
    public boolean isPluginEnabled(String pluginName) {
        return server.getPluginManager().isLoaded(pluginName);
    }

    @Override
    public Version getCurrentVersion() {
        return Version.fromString(BuildConstants.VERSION);
    }

    @Override
    public void runLater(Runnable runnable, long delay) {
        server.getScheduler()
                .buildTask(this, runnable)
                .delay(delay / 20L, TimeUnit.SECONDS)
                .schedule();
    }

    @Override
    public String getPlatformType() {
        return "proxy";
    }
}
