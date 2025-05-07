package net.skullian.anticrasher.velocity.user;

import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.api.user.UserManager;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VelocityUserManager implements UserManager {

    private final Map<UUID, User> userCache = new ConcurrentHashMap<>();

    @Override
    public @Nullable User get(UUID uuid) {
        if (uuid == null) return new VelocityUser(UUID.randomUUID(), null);

        return userCache.get(uuid);
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
