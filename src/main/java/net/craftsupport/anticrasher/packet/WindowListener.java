package net.craftsupport.anticrasher.packet;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import me.clip.placeholderapi.PlaceholderAPI;
import net.craftsupport.anticrasher.AntiCrasher;
import org.bukkit.Bukkit;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.bukkit.Bukkit.getLogger;

public class WindowListener implements PacketListener {
    private final AntiCrasher plugin;

    public WindowListener(final AntiCrasher plugin) {
        this.plugin = plugin;
    }

    public void onPacketReceive(PacketReceiveEvent event) {
        if (plugin.getConfig().getBoolean("windowclick-exploit")) {
            if (event.getPacketType() == PacketType.Play.Client.CLICK_WINDOW) {
                WrapperPlayClientClickWindow click = new WrapperPlayClientClickWindow(event);
                int clickType = click.getWindowClickType().ordinal();
                int button = click.getButton();
                int windowId = click.getWindowId();
                int slot = click.getSlot();

                if ((clickType == 1 || clickType == 2) && windowId >= 0 && button < 0) {
                    handleInvalidPacket(event);
                }

                else if (windowId >= 0 && clickType == 2 && slot < 0) {
                    handleInvalidPacket(event);
                }
            }
        }
    }

    public void handleInvalidPacket(PacketReceiveEvent event) {
        event.setCancelled(true);
        event.getUser().closeConnection();
        if (plugin.getConfig().getBoolean("log-to-file")) {
            try {
                log(event.getUser().getName()+"(UUID: " + event.getUser().getUUID() + ", IP Address: " + event.getUser().getAddress() + ")" + " Tried to use Window Click Crash Exploit");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (plugin.getConfig().getBoolean("log-attempts")) {
            getLogger().warning(event.getUser().getName() + " Tried to use the Crash Exploit");
        }
        if (plugin.getConfig().getBoolean("punish-on-attempt")) {
            String replacedString = plugin.getConfig().getString("punish-command").replace("%player%", event.getUser().getName());
            Bukkit.getScheduler().runTask(plugin, () -> {
                if (plugin.isPAPIEnabled()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(Bukkit.getOfflinePlayer(event.getUser().getUUID()), replacedString));
                } else {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replacedString);
                }
            });
        }
    }

    public void log(String message) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(plugin.getDataFolder().getPath() + "/LOGS", true));

            writer.write(message);
            writer.newLine();
            writer.close();

        } catch (IOException e) {
            getLogger().info(("An error occurred: " + e.getMessage()));
        }

    }
}

