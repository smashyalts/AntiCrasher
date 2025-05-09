package net.craftsupport.anticrasher.fabric.user;

import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.api.user.UserManager;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FabricUserManager implements UserManager {
    private final Map<UUID, User> userCache = new ConcurrentHashMap<>();

    @Override
    public User get(UUID uuid) {
        if (uuid == null) return new FabricUser(null, UUID.randomUUID(), null);

        return userCache.get(uuid);
    }

    @Override
    public @NotNull User getOrCreate(com.github.retrooper.packetevents.protocol.player.User user, Object source) {
        return userCache.containsKey(user.getUUID()) ? get(user.getUUID()) : create(user, source);
    }

    @Override
    public User create(com.github.retrooper.packetevents.protocol.player.User user, Object source) {
        return userCache.computeIfAbsent(user.getUUID(), id -> new FabricUser(user, id, source));
    }

    @Override
    public void invalidate(UUID uuid) {
        userCache.remove(uuid);
    }

    @Override
    public Collection<User> getOnlineUsers() {
        return userCache.values();
    }
}
