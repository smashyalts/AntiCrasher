package net.skullian.anticrasher.velocity.user;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.api.util.objects.Tuple;
import net.craftsupport.anticrasher.common.util.TextUtil;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.UUID;

public class VelocityUser extends User {

    private final com.github.retrooper.packetevents.protocol.player.User user;
    private final UUID uuid;

    private final CommandSource source;
    private final boolean bypass;

    public VelocityUser(com.github.retrooper.packetevents.protocol.player.User user, UUID uuid, Object source) {
        this.user = user;
        this.uuid = uuid;

        this.source = (CommandSource) source;
        this.bypass = this.source != null && this.source.hasPermission("anticrasher.bypass");
    }

    @Override
    public String getName() {
        Player player = asPlayer();
        return player != null ? player.getUsername() : getUniqueId().toString();
    }

    @SafeVarargs
    @Override
    public final void sendMessage(String message, Tuple<String, Object>... args) {
        source.sendMessage(TextUtil.text(message, args));
    }

    @SafeVarargs
    @Override
    public final void sendMessage(List<String> message, Tuple<String, Object>... args) {
        source.sendMessage(TextUtil.text(message, args));
    }

    @Override
    public void sendMessage(Component component) {
        source.sendMessage(component);
    }

    @Override
    public boolean hasPermission(String permissionNode) {
        return source.hasPermission(permissionNode);
    }

    @Override
    public com.github.retrooper.packetevents.protocol.player.User toPE() {
        return user;
    }

    @Override
    public Object getSource() {
        return source;
    }

    @Override
    public String processPlaceholders(String message) {
        return message;
    }

    @Override
    public boolean shouldBypass() {
        return bypass;
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    private Player asPlayer() { return source instanceof Player ? (Player) source : null; }
}
