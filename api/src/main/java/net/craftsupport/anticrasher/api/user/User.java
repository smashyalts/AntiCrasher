package net.craftsupport.anticrasher.api.user;

import lombok.Getter;
import net.craftsupport.anticrasher.api.util.objects.Tuple;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("unchecked")
@Getter
public abstract class User {

    private final UUID uniqueId;

    public User(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public abstract String getName();

    public abstract void sendMessage(String message, Tuple<String, Object>... args);

    public abstract void sendMessage(List<String> message, Tuple<String, Object>... args);

    public abstract void sendMessage(Component component);

    public abstract boolean hasPermission(String permissionNode);

    public abstract com.github.retrooper.packetevents.protocol.player.User toPE();

    public abstract Object getSource();

    public abstract String processPlaceholders(String message);

    public abstract boolean shouldBypass();
}
