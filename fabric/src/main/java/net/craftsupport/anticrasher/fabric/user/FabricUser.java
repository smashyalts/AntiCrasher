package net.craftsupport.anticrasher.fabric.user;

import com.github.retrooper.packetevents.PacketEvents;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.api.util.objects.Tuple;
import net.craftsupport.anticrasher.common.util.TextUtil;
import net.craftsupport.anticrasher.fabric.util.PlaceholderProcessor;
import net.kyori.adventure.text.Component;
import net.minecraft.server.command.ServerCommandSource;

import java.util.List;
import java.util.UUID;

public class FabricUser extends User {

    private final ServerCommandSource source;

    public FabricUser(UUID uniqueId, Object source) {
        super(uniqueId);
        this.source = (ServerCommandSource) source;
    }

    @Override
    public String getName() {
        return source.getName();
    }

    @Override
    public void sendMessage(String message, Tuple<String, Object>... args) {
        String parsedMessage = processPlaceholders(message);
        source.sendMessage(TextUtil.text(parsedMessage, args));
    }

    @Override
    public void sendMessage(List<String> messages, Tuple<String, Object>... args) {
        List<String> parsedMessages = messages.stream().map(this::processPlaceholders).toList();
        source.sendMessage(TextUtil.text(parsedMessages, args));
    }

    @Override
    public void sendMessage(Component component) {
        source.sendMessage(component);
    }

    @Override
    public boolean hasPermission(String permissionNode) {
        return Permissions.check(source, permissionNode);
    }

    @Override
    public com.github.retrooper.packetevents.protocol.player.User toPE() {
        return PacketEvents.getAPI().getPlayerManager().getUser(getUniqueId());
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
}
