package net.craftsupport.anticrasher.packet;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;
import me.clip.placeholderapi.PlaceholderAPI;
import net.craftsupport.anticrasher.AntiCrasher;
import net.craftsupport.anticrasher.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import static org.bukkit.Bukkit.getLogger;
import java.io.IOException;


public class ChannelListener implements PacketListener {
    private final AntiCrasher plugin;
    private final Utils utilsInstance;

    public ChannelListener(AntiCrasher plugin, Utils util) {
        this.plugin = plugin;
        this.utilsInstance = util;
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.PLUGIN_MESSAGE) {
            WrapperPlayClientPluginMessage wrapper = new WrapperPlayClientPluginMessage(event);
            String channel = wrapper.getChannelName();
            byte[] data = wrapper.getData();
            Player player = (Player) event.getPlayer();

            String channelLower = channel.toLowerCase();
            if (!channel.equals(channelLower) || !player.hasPermission("anticrasher.bypass")) {
                handleInvalidPacket(event);
                return;
            }

            boolean isRegisterChannel = false;
            if (channelLower.contains(":")) {
                String[] parts = channelLower.split(":", 2);
                if (parts.length == 2 && (parts[1].equals("register") || parts[1].equals("unregister"))) {
                    isRegisterChannel = true;
                }
            } else {
                if (channelLower.equals("register") || channelLower.equals("unregister")) {
                    isRegisterChannel = true;
                }
            }

            if (isRegisterChannel && data.length > 64 && !player.hasPermission("anticrasher.bypass")) {
                handleInvalidPacket(event);
            }
        }
    }

    public void handleInvalidPacket(PacketReceiveEvent event) {
        event.setCancelled(true);
        event.getUser().closeConnection();

        if (utilsInstance.logToFile) {
            try {
                utilsInstance.log(event.getUser().getName() + " Tried to use the Crash Exploit");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (utilsInstance.logAttempts) {
            getLogger().warning(event.getUser().getName() + " Tried to use the Crash Exploit");
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
