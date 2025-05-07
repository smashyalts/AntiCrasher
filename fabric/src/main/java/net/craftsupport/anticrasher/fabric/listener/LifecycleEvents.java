package net.craftsupport.anticrasher.fabric.listener;

import info.preva1l.trashcan.flavor.annotations.Configure;
import info.preva1l.trashcan.flavor.annotations.Service;
import net.craftsupport.anticrasher.fabric.AntiCrasher;
import net.craftsupport.anticrasher.fabric.service.ServiceManager;
import net.craftsupport.anticrasher.fabric.user.FabricUser;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import java.util.UUID;

@Service
public class LifecycleEvents {
    public static final LifecycleEvents instance = new LifecycleEvents();

    @Configure
    public void listen() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            AntiCrasher.server = server;
            AntiCrasher.instance.setConsoleUser(new FabricUser(UUID.randomUUID(), server.getCommandSource())); // cannot do in onInitialize
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            ServiceManager.onDisable();
        });
    }
}
