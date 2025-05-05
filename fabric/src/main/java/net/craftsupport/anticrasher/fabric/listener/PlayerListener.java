package net.craftsupport.anticrasher.fabric.listener;

import info.preva1l.trashcan.flavor.annotations.Configure;
import info.preva1l.trashcan.flavor.annotations.Service;
import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.common.update.UpdateChecker;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

@Service
public class PlayerListener {

    @Configure
    public void listen() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            User user = AntiCrasherAPI.getInstance().getUserManager().create(player.getUuid(), player);

            if (user.hasPermission("anticrasher.notify.updates")) {
                UpdateChecker.getInstance().sendNotification(user);
            }
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            AntiCrasherAPI.getInstance().getUserManager().invalidate(handler.getPlayer().getUuid());
        });
    }
}
