package net.craftsupport.anticrasher.fabric.listener;

import info.preva1l.trashcan.flavor.annotations.Configure;
import info.preva1l.trashcan.flavor.annotations.Service;
import net.craftsupport.anticrasher.fabric.AntiCrasher;
import net.craftsupport.anticrasher.fabric.service.ServiceManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

@Service
public class LifecycleListener {

    @Configure
    public static void listen() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            AntiCrasher.server = server;
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            ServiceManager.onDisable();
        });
    }
}
