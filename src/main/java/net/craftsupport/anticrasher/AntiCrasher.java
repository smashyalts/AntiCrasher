package net.craftsupport.anticrasher;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.bstats.Metrics;
import net.craftsupport.anticrasher.packet.packetstuff;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;

public final class AntiCrasher extends JavaPlugin {
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
        saveDefaultConfig();
        int pluginId = 20218;
        Metrics metrics = new Metrics(this, pluginId);
        PacketEvents.getAPI().getEventManager().registerListener(new packetstuff(), PacketListenerPriority.LOW);
        PacketEvents.getAPI().init();
    }
    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }
}
