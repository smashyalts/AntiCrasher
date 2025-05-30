package net.craftsupport.anticrasher.fabric.listener;

import com.github.retrooper.packetevents.PacketEvents;
import info.preva1l.trashcan.flavor.annotations.Configure;
import info.preva1l.trashcan.flavor.annotations.Service;
import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.common.update.UpdateChecker;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

@Service
public class PlayerEvents {
    public static PlayerEvents instance = new PlayerEvents();

    @Configure
    public void listen() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            User user = AntiCrasherAPI.getInstance().getUserManager().create(
                    player.getUuid(),
                    player.getCommandSource());

            if (user.hasPermission("anticrasher.updates")) {
                UpdateChecker.getInstance().sendNotification(user);
            }
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            AntiCrasherAPI.getInstance().getUserManager().invalidate(handler.getPlayer().getUuid());
        });
    }
}
