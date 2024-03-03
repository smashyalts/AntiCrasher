package net.smashyalts.anticrasher;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.jeff_media.updatechecker.UpdateCheckSource;
import com.jeff_media.updatechecker.UpdateChecker;
import com.jeff_media.updatechecker.UserAgentBuilder;
import io.github.retrooper.packetevents.bstats.Metrics;
import net.smashyalts.anticrasher.listeners.TabCompleteListener;
import net.smashyalts.anticrasher.listeners.WindowListener;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;

import net.smashyalts.anticrasher.utils.getBukkitVersion;

public final class AntiCrasher extends JavaPlugin {
    private boolean isPAPIEnabled;
    private static final String SPIGOT_RESOURCE_ID = "113404";

    @Override
    public void onLoad() {

        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().getSettings().reEncodeByDefault(false)
                .checkForUpdates(true)
                .bStats(true);
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        isPAPIEnabled = getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");

        saveDefaultConfig();

        new Metrics(this, 20218);

        PacketEvents.getAPI().getEventManager().registerListener(new WindowListener(this), PacketListenerPriority.LOWEST);
        PacketEvents.getAPI().getEventManager().registerListener(new TabCompleteListener(this), PacketListenerPriority.LOWEST);
        PacketEvents.getAPI().init();

        // Update is always right
        new UpdateChecker(this, UpdateCheckSource.SPIGOT, SPIGOT_RESOURCE_ID)
                .setUserAgent(new UserAgentBuilder().addPluginNameAndVersion())
                .setNotifyByPermissionOnJoin("anticrasher.updatechecker")
                .setNotifyOpsOnJoin(true)
                .setDownloadLink("https://www.spigotmc.org/resources/anticrasher.113404/")
                .checkNow();

        // Checking bukkit version for handling unexpected crash
        new getBukkitVersion();
    }
    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }

    public boolean isPAPIEnabled() {
        return isPAPIEnabled;
    }
}
