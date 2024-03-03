package net.craftsupport.anticrasher;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.bstats.Metrics;
import net.craftsupport.anticrasher.packet.TabCompleteListener;
import net.craftsupport.anticrasher.packet.WindowListener;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;

public final class AntiCrasher extends JavaPlugin {
    private boolean isPAPIEnabled;

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
        getCommand("acreload").setExecutor(new net.craftsupport.anticrasher.commands.reload(this));
        isPAPIEnabled = getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
        saveDefaultConfig();

        new Metrics(this, 20218);

        PacketEvents.getAPI().getEventManager().registerListener(new WindowListener(this), PacketListenerPriority.LOWEST);
        PacketEvents.getAPI().getEventManager().registerListener(new TabCompleteListener(this), PacketListenerPriority.LOWEST);
        PacketEvents.getAPI().init();
    }


    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }

    public boolean isPAPIEnabled() {
        return isPAPIEnabled;
    }
}
