package net.craftsupport.anticrasher.bukkit;

import com.github.puregero.multilib.MultiLib;
import com.github.retrooper.packetevents.PacketEvents;
import info.preva1l.trashcan.Version;
import info.preva1l.trashcan.flavor.Flavor;
import info.preva1l.trashcan.flavor.FlavorOptions;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.Platform;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.common.manager.CheckManager;
import net.craftsupport.anticrasher.common.util.ACLogger;
import net.craftsupport.anticrasher.bukkit.api.BukkitAntiCrasherAPI;
import net.craftsupport.anticrasher.bukkit.listener.PlayerEvents;
import net.craftsupport.anticrasher.bukkit.user.BukkitUser;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.UUID;

public class AntiCrasher extends JavaPlugin implements Platform {

    @Getter public static AntiCrasher instance;
    private User consoleUser;

    protected Flavor flavor;

    public AntiCrasher() {
        instance = this;
    }

    @Override
    public Path getConfigDirectory() {
        return getDataFolder().toPath();
    }

    @Override
    public void onLoad() {
        this.flavor = Flavor.create(
                this.getClass(),
                new FlavorOptions(
                        this.getLogger(),
                        this.getClass().getPackageName()
                )
        );

        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().getSettings().reEncodeByDefault(false)
                .checkForUpdates(true)
                .bStats(true)
                .kickOnPacketException(true);

        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        ACLogger.info("Enabling AntiCrasher...");
        PacketEvents.getAPI().init();
        AntiCrasherAPI.setInstance(new BukkitAntiCrasherAPI());

        ACLogger.info("Initialising Metrics.");
        new Metrics(this, 20218);

        flavor.startup();
        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);

        this.consoleUser = new BukkitUser(null, UUID.randomUUID(), Bukkit.getConsoleSender());

        ACLogger.info("AntiCrasher enabled with %s checks enabled.".formatted(CheckManager.getInstance().checks.size()));
    }

    @Override
    public void onDisable() {
        flavor.close();

        PacketEvents.getAPI().terminate();

        ACLogger.info("AntiCrasher has disabled.");
    }

    @Override
    public boolean isPluginEnabled(String pluginName) {
        return getServer().getPluginManager().isPluginEnabled(pluginName);
    }

    @Override
    public Version getCurrentVersion() {
        return Version.fromString(getDescription().getVersion());
    }

    @Override
    public void runLater(Runnable runnable, long delay) {
        MultiLib.getGlobalRegionScheduler().runDelayed(this, (task) -> runnable.run(), delay);
    }

    @Override
    public User getConsoleUser() {
        return consoleUser;
    }

    @Override
    public String getPlatformType() {
        return "plugin";
    }
}
