package net.craftsupport.anticrasher.fabric.api;

import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.Platform;
import net.craftsupport.anticrasher.api.event.bus.EventBus;
import net.craftsupport.anticrasher.api.user.UserManager;
import net.craftsupport.anticrasher.common.event.bus.EventBusImpl;
import net.craftsupport.anticrasher.fabric.AntiCrasher;
import net.craftsupport.anticrasher.fabric.user.FabricUserManager;

public class FabricAntiCrasherAPI extends AntiCrasherAPI {

    private final UserManager userManager;
    private final EventBusImpl eventBus;

    public FabricAntiCrasherAPI() {
        this.userManager = new FabricUserManager();
        this.eventBus = new EventBusImpl();
    }

    @Override
    public Platform getPlatform() {
        return AntiCrasher.instance;
    }

    @Override
    public UserManager getUserManager() {
        return userManager;
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }
}
