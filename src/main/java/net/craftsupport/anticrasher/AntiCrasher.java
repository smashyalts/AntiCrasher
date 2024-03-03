package net.craftsupport.anticrasher;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.bstats.Metrics;
import net.craftsupport.anticrasher.commands.reloadCommand;
import net.craftsupport.anticrasher.packet.TabCompleteListener;
import net.craftsupport.anticrasher.packet.WindowListener;
import net.craftsupport.anticrasher.utils.utils;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;

public final class AntiCrasher extends JavaPlugin {
    private boolean isPAPIEnabled;

    public utils utilsInstance;

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

        utilsInstance = new utils(this);
        getCommand("acreload").setExecutor(new reloadCommand(this, utilsInstance));

        new Metrics(this, 20218);

        PacketEvents.getAPI().getEventManager().registerListener(new WindowListener(this, utilsInstance), PacketListenerPriority.LOWEST);
        PacketEvents.getAPI().getEventManager().registerListener(new TabCompleteListener(this, utilsInstance), PacketListenerPriority.LOWEST);
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
