package net.craftsupport.anticrasher;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import net.craftsupport.anticrasher.commands.ReloadCommand;
import net.craftsupport.anticrasher.packet.impl.BundleListener;
import net.craftsupport.anticrasher.packet.impl.ChannelListener;
import net.craftsupport.anticrasher.packet.impl.TabCompleteListener;
import net.craftsupport.anticrasher.packet.impl.WindowListener;
import net.craftsupport.anticrasher.utils.Utils;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;

public final class AntiCrasher extends JavaPlugin {

    private boolean isPAPIEnabled;
    private Utils utilsInstance;

    private static AntiCrasher instance;

    @Override
    public void onLoad() {
        instance = this;

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

        PacketEvents.getAPI().getEventManager().registerListener(new WindowListener(), PacketListenerPriority.LOWEST);
        PacketEvents.getAPI().getEventManager().registerListener(new TabCompleteListener(), PacketListenerPriority.LOWEST);
        PacketEvents.getAPI().getEventManager().registerListener(new ChannelListener(), PacketListenerPriority.LOWEST);
        PacketEvents.getAPI().getEventManager().registerListener(new BundleListener(), PacketListenerPriority.LOWEST);
        PacketEvents.getAPI().init();
    }


    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }

    public Utils getUtils() {
        return utilsInstance;
    }

    public boolean isPAPIEnabled() {
        return isPAPIEnabled;
    }

    public static AntiCrasher getInstance() {
        return instance;
    }
}
