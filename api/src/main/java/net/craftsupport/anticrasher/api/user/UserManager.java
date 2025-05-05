package net.craftsupport.anticrasher.api.user;

import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public interface UserManager {

    @Nullable User get(UUID uuid);

    User create(UUID uuid, Object source);

    void invalidate(UUID uuid);

    Collection<User> getOnlineUsers();
}
