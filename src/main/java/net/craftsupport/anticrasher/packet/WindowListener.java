package net.craftsupport.anticrasher.packet;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.item.type.ItemType;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEditBook;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetSlot;
import me.clip.placeholderapi.PlaceholderAPI;
import net.craftsupport.anticrasher.AntiCrasher;
import net.craftsupport.anticrasher.utils.utils;
import org.bukkit.Bukkit;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.bukkit.Bukkit.getLogger;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
public class WindowListener implements PacketListener {
    private final AntiCrasher plugin;
    private final utils utilsInstance;
    public WindowListener(final AntiCrasher plugin, utils util) {
        this.plugin = plugin;
        this.utilsInstance = util;
    }

    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getServerVersion().isOlderThan(ServerVersion.V_1_20_5)){
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
                }};
            }

        if (event.getPacketType() == PacketType.Play.Client.EDIT_BOOK) {
            WrapperPlayClientEditBook editBook = new WrapperPlayClientEditBook(event);
            if (editBook.getTitle().length() > 32) {
                handleInvalidPacket(event);
            }
        }
    }

    public void handleInvalidPacket(PacketReceiveEvent event) {
        event.setCancelled(true);
        event.getUser().closeConnection();
        if (utilsInstance.logtofile) {
            try {
                log(event.getUser().getName() + " Tried to use the Crash Exploit");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (utilsInstance.logattempts) {
            getLogger().warning(event.getUser().getName() + " Tried to use the Crash Exploit");
        }
        if (utilsInstance.punishonattempt) {
            String replacedString = utilsInstance.punishcommand.replace("%player%", event.getUser().getName());
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(utilsInstance.dataFolder + "/LOGS", true));

            String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            writer.write(timestamp + " - " + message);
            writer.newLine();
            writer.close();

        } catch (IOException e) {
            getLogger().info(("An error occurred: " + e.getMessage()));
        }
    }
}

