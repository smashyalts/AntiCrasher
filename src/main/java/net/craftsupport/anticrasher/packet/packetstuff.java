package net.craftsupport.anticrasher.packet;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import me.clip.placeholderapi.PlaceholderAPI;
import net.craftsupport.anticrasher.AntiCrasher;
import org.bukkit.Bukkit;

public class packetstuff implements PacketListener {
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.CLICK_WINDOW) {
            WrapperPlayClientClickWindow click = new WrapperPlayClientClickWindow(event);
            int clickType = click.getWindowClickType().ordinal();
            int button = click.getButton();
            int windowId = click.getWindowId();

            if ((clickType == 1 || clickType == 2) && windowId >= 0 && button < 0) {
                event.setCancelled(true);
                if (AntiCrasher.getPlugin(AntiCrasher.class).getConfig().getBoolean("log-attempts")) {
                    Bukkit.getLogger().warning(event.getUser().getName() + " Tried to use the Crash Exploit");
                }
                if (AntiCrasher.getPlugin(AntiCrasher.class).getConfig().getBoolean("punish-on-attempt")) {
                    String ReplacedString = AntiCrasher.getPlugin(AntiCrasher.class).getConfig().getString("punish-command").replaceAll("%player%", event.getUser().getName());
                    Bukkit.getScheduler().runTask(AntiCrasher.getPlugin(AntiCrasher.class), () -> {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(Bukkit.getOfflinePlayer(event.getUser().getUUID()), ReplacedString));
                    });
                }
            }
        }

    }
}

