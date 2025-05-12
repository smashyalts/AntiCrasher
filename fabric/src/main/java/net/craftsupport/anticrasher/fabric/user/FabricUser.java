package net.craftsupport.anticrasher.fabric.user;

import com.github.retrooper.packetevents.PacketEvents;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.api.util.objects.Tuple;
import net.craftsupport.anticrasher.common.util.TextUtil;
import net.craftsupport.anticrasher.fabric.AntiCrasher;
import net.craftsupport.anticrasher.fabric.util.PlaceholderProcessor;
import net.kyori.adventure.text.Component;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;
import java.util.UUID;

public class FabricUser extends User {

    private final com.github.retrooper.packetevents.protocol.player.User user;
    private final UUID uuid;

    private final ServerCommandSource source;
    private final boolean bypass;

    public FabricUser(UUID uuid, Object source) {
        ServerPlayerEntity entity = AntiCrasher.server.getPlayerManager().getPlayer(uuid);
        this.user = entity != null ? PacketEvents.getAPI().getPlayerManager().getUser(entity) : null;

        this.uuid = uuid;
        this.source = (ServerCommandSource) source;
        this.bypass = source != null && Permissions.check(this.source, "anticrasher.bypass");
    }

    @Override
    public String getName() {
        return source != null ? source.getName() : getUniqueId().toString();
    }

    @SafeVarargs
    @Override
    public final void sendMessage(String message, Tuple<String, Object>... args) {
        String parsedMessage = processPlaceholders(message);
        source.sendMessage(TextUtil.text(parsedMessage, args));
    }

    @SafeVarargs
    @Override
    public final void sendMessage(List<String> messages, Tuple<String, Object>... args) {
        List<String> parsedMessages = messages.stream().map(this::processPlaceholders).toList();
        source.sendMessage(TextUtil.text(parsedMessages, args));
    }

    @Override
    public void sendMessage(Component component) {
        source.sendMessage(component);
    }

    @Override
    public boolean hasPermission(String permissionNode) {
        return source != null && (Permissions.check(source, permissionNode) || source.hasPermissionLevel(3)); // check if OP
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
        if (AntiCrasherAPI.getInstance().getPlatform().isPluginEnabled("placeholder-api")) {
            return PlaceholderProcessor.processPlaceholders(message, source);
        }

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
}
