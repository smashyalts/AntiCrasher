package net.craftsupport.anticrasher;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import net.craftsupport.anticrasher.commands.ReloadCommand;
import net.craftsupport.anticrasher.packet.ChannelListener;
import net.craftsupport.anticrasher.packet.TabCompleteListener;
import net.craftsupport.anticrasher.packet.WindowListener;
import net.craftsupport.anticrasher.utils.Utils;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;

public final class AntiCrasher extends JavaPlugin {
    private boolean isPAPIEnabled;

    public Utils utilsInstance;

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().getSettings().reEncodeByDefault(false)
                .checkForUpdates(true)
                .bStats(true)
                .kickOnPacketException(true);

        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        isPAPIEnabled = getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");

        saveDefaultConfig();

        utilsInstance = new Utils(this);
        getCommand("acreload").setExecutor(new ReloadCommand(this, utilsInstance));

        new Metrics(this, 20218);

        PacketEvents.getAPI().getEventManager().registerListener(new WindowListener(this, utilsInstance), PacketListenerPriority.LOWEST);
        PacketEvents.getAPI().getEventManager().registerListener(new TabCompleteListener(this, utilsInstance), PacketListenerPriority.LOWEST);
        PacketEvents.getAPI().getEventManager().registerListener(new ChannelListener(this, utilsInstance), PacketListenerPriority.LOWEST);
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
