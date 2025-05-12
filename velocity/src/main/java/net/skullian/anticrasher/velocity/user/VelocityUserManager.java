package net.skullian.anticrasher.velocity.user;

import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.api.user.UserManager;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VelocityUserManager implements UserManager {

    private final Map<UUID, User> userCache = new ConcurrentHashMap<>();

    @Override
    public User get(UUID uuid) {
        if (uuid == null) return new VelocityUser(UUID.randomUUID(), null);

        return userCache.get(uuid);
    }

    @Override
    public @NotNull User getOrCreate(UUID uuid, Object source) {
        return userCache.containsKey(uuid) ? get(uuid) : create(uuid, source);
    }

    @Override
    public User create(UUID uuid, Object source) {
        return userCache.computeIfAbsent(uuid, id -> new VelocityUser(id, source));
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
