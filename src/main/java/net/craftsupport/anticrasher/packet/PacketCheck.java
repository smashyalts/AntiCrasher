package net.craftsupport.anticrasher.packet;

import com.github.puregero.multilib.MultiLib;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.craftsupport.anticrasher.AntiCrasher;
import net.craftsupport.anticrasher.utils.Utils;
import org.bukkit.Bukkit;

import static org.bukkit.Bukkit.getLogger;

public abstract class PacketCheck implements PacketListener {

    private final String exploitName;
    private final Utils utils;

    public PacketCheck(String exploitName) {
        this.exploitName = exploitName;

        this.utils = AntiCrasher.getInstance().getUtils();
    }

    public void fail(PacketReceiveEvent event) {
        event.setCancelled(true);
        event.getUser().closeConnection();

        String message = String.format("%s Tried to use the %s Exploit", event.getUser().getName(), exploitName);

        if (utils.logToFile) {
            utils.log(message);
        }
        if (utils.logAttempts) {
            getLogger().warning(message);
        }

        if (utils.punishOnAttempt) {
            String replacedString = utils.punishCommand.replace("%player%", event.getUser().getName());
            MultiLib.getGlobalRegionScheduler().run(AntiCrasher.getInstance(), (task) -> {
                if (AntiCrasher.getInstance().isPAPIEnabled()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(Bukkit.getOfflinePlayer(event.getUser().getUUID()), replacedString));
                } else {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replacedString);
                }
            });
        }
    }
}
