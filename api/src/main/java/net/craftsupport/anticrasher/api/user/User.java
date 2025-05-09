package net.craftsupport.anticrasher.api.user;

import lombok.Getter;
import net.craftsupport.anticrasher.api.util.objects.Tuple;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.UUID;

/**
 * Represents a user playing on the server.
 * You can interact with the user using this class.
 */
@SuppressWarnings("unchecked")
@Getter
public abstract class User {

    /**
     * Gets the name of the user.
     * This is typically the username of the user, unless they are in the Configuration phase where it will return the UUID string.
     *
     * @return The name of the user.
     */
    public abstract String getName();

    /**
     * Sends a single string message (converted into an Adventure {@link Component}) to a player.
     *
     * @param message The string message to send to the user. - MiniMessage supported
     * @param args Tuple.of("replacement", "value")
     */
    public abstract void sendMessage(String message, Tuple<String, Object>... args);

    /**
     * Sends a multi-line string message (converted into an Adventure {@link Component}) to a player.
     *
     * @param message The {@link List<String>} message to send to the user. - MiniMessage supported
     * @param args Tuple.of("replacement", "value")
     */
    public abstract void sendMessage(List<String> message, Tuple<String, Object>... args);

    /**
     * Send an already-processed {@link Component} to the user.
     * @param component The {@link Component} to send to the user.
     */
    public abstract void sendMessage(Component component);

    /**
     * Check if the user has a specific permission node. This will return false if the user is in the configuration phase.
     * @param permissionNode The permission node to check for.
     * @return Whether the user has the permission node or not.
     */
    public abstract boolean hasPermission(String permissionNode);

    /**
     * Get the PacketEvents {@link User} object for this user.
     * @return {@link User}
     */
    public abstract com.github.retrooper.packetevents.protocol.player.User toPE();

    /**
     * Get the original source class of this user.
     * For Paper, this would be CommandSource.
     * For Fabric, this would be ServerCommandSource.
     *
     * @return The original Object source.
     */
    public abstract Object getSource();

    /**
     * Uses the platform-dependent placeholder processor mod/plugin (PlaceholderAPI, TextPlaceholderAPI) to process the placeholders in the message.
     * @param message The message to process.
     * @return The processed message with placeholders replaced.
     */
    public abstract String processPlaceholders(String message);

    /**
     * In the actual implementation, the bypass permission is checked and cached on join, otherwise we would be checking it on every packet which is not efficient.
     * @return Whether the user should bypass the checks or not.
     */
    public abstract boolean shouldBypass();

    /**
     * Get the {@link UUID} of the user.
     */
    public abstract UUID getUniqueId();
}
