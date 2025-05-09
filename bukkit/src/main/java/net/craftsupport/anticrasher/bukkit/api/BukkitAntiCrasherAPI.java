package net.craftsupport.anticrasher.bukkit.api;

import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.Platform;
import net.craftsupport.anticrasher.api.event.bus.EventBus;
import net.craftsupport.anticrasher.api.user.UserManager;
import net.craftsupport.anticrasher.common.event.bus.EventBusImpl;
import net.craftsupport.anticrasher.bukkit.AntiCrasher;
import net.craftsupport.anticrasher.bukkit.user.BukkitUserManager;

public class BukkitAntiCrasherAPI extends AntiCrasherAPI {

    private final UserManager userManager;
    private final EventBusImpl eventBus;

    public BukkitAntiCrasherAPI() {
        this.userManager = new BukkitUserManager();
        this.eventBus = new EventBusImpl();
    }

    @Override
    public Platform getPlatform() {
        return AntiCrasher.getInstance();
    }

    @Override
    public UserManager getUserManager() {
        return this.userManager;
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }
}
