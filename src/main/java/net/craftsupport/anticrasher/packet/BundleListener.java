package net.craftsupport.anticrasher.packet;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientSelectBundleItem;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBundle;
import me.clip.placeholderapi.PlaceholderAPI;
import net.craftsupport.anticrasher.AntiCrasher;
import net.craftsupport.anticrasher.utils.Utils;
import org.bukkit.Bukkit;

import static org.bukkit.Bukkit.getLogger;

public class BundleListener implements PacketListener {

    private final AntiCrasher plugin;
    private final Utils utilsInstance;

    public BundleListener(AntiCrasher plugin, Utils util) {
        this.plugin = plugin;
        this.utilsInstance = util;
    }


    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.SELECT_BUNDLE_ITEM) {
            WrapperPlayClientSelectBundleItem packet = new WrapperPlayClientSelectBundleItem(event);

            if (packet.getSelectedItemIndex() < 0 && packet.getSelectedItemIndex() != -1) {
                handleInvalidPacket(event);
            }
        }
    }

    public void handleInvalidPacket(PacketReceiveEvent event) {
        event.setCancelled(true);
        event.getUser().closeConnection();

        if (utilsInstance.logToFile) {
            utilsInstance.log(event.getUser().getName() + " Tried to use the Bundle Crash Exploit");
        }

        if (utilsInstance.logAttempts) {
            getLogger().warning(event.getUser().getName() + " Tried to use the Bundle Crash Exploit");
        }
        if (utilsInstance.punishOnAttempt) {
            String replacedString = utilsInstance.punishCommand.replace("%player%", event.getUser().getName());
            Bukkit.getScheduler().runTask(plugin, () -> {
                if (plugin.isPAPIEnabled()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(Bukkit.getOfflinePlayer(event.getUser().getUUID()), replacedString));
                } else {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replacedString);
                }
            });
        }
    }
}
