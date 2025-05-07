package net.skullian.anticrasher.velocity.api;

import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.Platform;
import net.craftsupport.anticrasher.api.event.bus.EventBus;
import net.craftsupport.anticrasher.api.user.UserManager;
import net.craftsupport.anticrasher.common.event.bus.EventBusImpl;
import net.skullian.anticrasher.velocity.AntiCrasher;
import net.skullian.anticrasher.velocity.user.VelocityUserManager;

public class VelocityAntiCrasherAPI extends AntiCrasherAPI {

    private final UserManager userManager;
    private final EventBusImpl eventBus;

    public VelocityAntiCrasherAPI() {
        this.userManager = new VelocityUserManager();
        this.eventBus = new EventBusImpl();
    }

    @Override
    public Platform getPlatform() {
        return AntiCrasher.getInstance();
    }

    @Override
    public UserManager getUserManager() {
        return null;
    }

    @Override
    public EventBus getEventBus() {
        return null;
    }
}
