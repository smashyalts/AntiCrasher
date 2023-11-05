package net.craftsupport.anticrasher.packet;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import net.craftsupport.anticrasher.AntiCrasher;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import sun.util.calendar.BaseCalendar;

import java.util.Date;

public class packetstuff implements PacketListener {
    BanList banlist = Bukkit.getBanList(BanList.Type.NAME);
    BanList banlistip = Bukkit.getBanList(BanList.Type.IP);
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
if (AntiCrasher.getPlugin(AntiCrasher.class).getConfig().getBoolean("ban-on-attempt")) {
    if (AntiCrasher.getPlugin(AntiCrasher.class).getConfig().getBoolean("ip-ban")) {
        banlistip.addBan(event.getUser().getAddress().toString(), AntiCrasher.getPlugin(AntiCrasher.class).getConfig().getString("ban-on-attempt-message"), null, "AntiCrasher");
    }
    else banlist.addBan(event.getUser().getName(), AntiCrasher.getPlugin(AntiCrasher.class).getConfig().getString("ban-on-attempt-message"), null, "AntiCrasher");
}

                }
            }
        }
    }

