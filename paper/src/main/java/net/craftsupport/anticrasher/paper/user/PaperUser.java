package net.craftsupport.anticrasher.paper.user;

import com.github.retrooper.packetevents.PacketEvents;
import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.api.util.objects.Tuple;
import net.craftsupport.anticrasher.common.util.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@Getter
public class PaperUser extends User {

    private final CommandSender source;

    public PaperUser(UUID uniqueId, Object source) {
        super(uniqueId);
        this.source = (CommandSender) source;
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
        return source != null && source.hasPermission(permissionNode);
    }

    @Override
    public com.github.retrooper.packetevents.protocol.player.User toPE() {
        return PacketEvents.getAPI().getPlayerManager().getUser(getUniqueId());
    }

    @Override
    public String processPlaceholders(String message) {
        if (AntiCrasherAPI.getInstance().getPlatform().isPluginEnabled("PlaceholderAPI") && source != null) {
            return PlaceholderAPI.setPlaceholders(asPlayer(), message);
        }

        return message;
    }

    private Player asPlayer() {
        return source instanceof Player ? (Player) source : null;
    }
}