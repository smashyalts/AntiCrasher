package net.skullian.anticrasher.velocity.listener;

import com.github.retrooper.packetevents.PacketEvents;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.common.update.UpdateChecker;

public class PlayerEvents {

    @Subscribe
    public void onPlayerJoin(LoginEvent event) {
        User user = AntiCrasherAPI.getInstance().getUserManager().get(event.getPlayer().getUniqueId());
        if (user == null)
            user = AntiCrasherAPI.getInstance().getUserManager().create(
                    PacketEvents.getAPI().getPlayerManager().getUser(event.getPlayer()),
                    event.getPlayer()
            );

        if (user.hasPermission("anticrasher.updates")) {
            UpdateChecker.getInstance().sendNotification(user);
        }
    }

    @Subscribe
    public void onPlayerLeave(DisconnectEvent event) {
        AntiCrasherAPI.getInstance().getUserManager().invalidate(event.getPlayer().getUniqueId());
    }
}
