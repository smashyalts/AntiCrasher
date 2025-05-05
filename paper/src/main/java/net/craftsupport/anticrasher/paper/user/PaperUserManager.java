package net.craftsupport.anticrasher.paper.user;

import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.api.user.UserManager;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PaperUserManager implements UserManager {

    private final Map<UUID, User> userCache = new ConcurrentHashMap<>();

    @Override
    public User get(UUID uuid) {
        return userCache.get(uuid);
    }

    @Override
    public User create(UUID uuid, Object source) {
        return userCache.computeIfAbsent(uuid, id -> new PaperUser(id, source));
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
