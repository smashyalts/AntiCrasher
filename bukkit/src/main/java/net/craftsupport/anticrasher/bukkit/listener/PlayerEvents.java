package net.craftsupport.anticrasher.bukkit.listener;

import com.github.retrooper.packetevents.PacketEvents;
import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.common.update.UpdateChecker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = AntiCrasherAPI.getInstance().getUserManager().create(
                player.getUniqueId(),
                player
        );

        if (player.hasPermission("anticrasher.updates")) {
            UpdateChecker.getInstance().sendNotification(user);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        AntiCrasherAPI.getInstance().getUserManager().invalidate(event.getPlayer().getUniqueId());
    }
}
