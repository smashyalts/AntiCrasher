package net.craftsupport.anticrasher.api.user;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public interface UserManager {

    /**
     * Get a user by their UUID.
     * @param uuid The UUID of the user.
     * @return The user, or null if the user is not online.
     */
    @Nullable User get(UUID uuid);

    /**
     * Fetch an already cached user, otherwise create one.
     * Do not use this unless necessary.
     * @param user PE User of player.
     * @param source Original platform source.
     * @return {@link User} instance.
     */
    @NotNull User getOrCreate(com.github.retrooper.packetevents.protocol.player.User user, Object source);

    /**
     * Create a new {@link User} object, and cache it.
     * This is typically referenced on join.
     * @param user The PacketEvents user.
     * @param source The original source of the user. E.g. CommandSource for Paper.
     * @return The user object.
     */
    User create(com.github.retrooper.packetevents.protocol.player.User user, Object source);

    /**
     * Invalidate a user object.
     * This is typically called on player quit.
     *
     * @param uuid The UUID of the user.
     */
    void invalidate(UUID uuid);

    /**
     * Get all online users.
     * @return A collection of all online users.
     */
    Collection<User> getOnlineUsers();
}
