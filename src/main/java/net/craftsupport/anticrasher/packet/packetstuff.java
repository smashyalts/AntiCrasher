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

public class packetstuff implements PacketListener {
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.CLICK_WINDOW) {
            WrapperPlayClientClickWindow click = new WrapperPlayClientClickWindow(event);
            int clickType = click.getWindowClickType().ordinal();
            int button = click.getButton();
            int windowId = click.getWindowId();
            int slot = click.getSlot();

            if ((clickType == 1 || clickType == 2) && windowId >= 0 && (slot < 0 || button < 0)) {
                event.setCancelled(true);
                event.getUser().closeConnection();
                if (AntiCrasher.getPlugin(AntiCrasher.class).getConfig().getBoolean("log-to-file")) {
                    try {
                        log(event.getUser().getName() + " Tried to use the Crash Exploit");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (AntiCrasher.getPlugin(AntiCrasher.class).getConfig().getBoolean("log-attempts")) {
                    getLogger().warning(event.getUser().getName() + " Tried to use the Crash Exploit");
                }
                if (AntiCrasher.getPlugin(AntiCrasher.class).getConfig().getBoolean("punish-on-attempt")) {
                    String ReplacedString = AntiCrasher.getPlugin(AntiCrasher.class).getConfig().getString("punish-command").replaceAll("%player%", event.getUser().getName());
                    Bukkit.getScheduler().runTask(AntiCrasher.getPlugin(AntiCrasher.class), () -> {
                        if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI").isEnabled()) {Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(Bukkit.getOfflinePlayer(event.getUser().getUUID()), ReplacedString));
                        }
                        else {Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ReplacedString);}
                    });
                }
            }
        }


    }
    public void log(String message) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(AntiCrasher.getPlugin(AntiCrasher.class).getDataFolder().getPath() + "/LOGS", true));

            writer.write(message);
            writer.newLine();
            writer.close();

        } catch (IOException e) {
            getLogger().info(("An error occurred: " + e.getMessage()));
        }

    }
}

