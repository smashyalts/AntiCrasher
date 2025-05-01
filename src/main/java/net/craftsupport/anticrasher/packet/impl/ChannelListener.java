package net.craftsupport.anticrasher.packet.impl;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.ConnectionState;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;
import net.craftsupport.anticrasher.packet.PacketCheck;
import org.bukkit.entity.Player;

public class ChannelListener extends PacketCheck {

    public ChannelListener() {
        super("Plugin Message Channel");
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.PLUGIN_MESSAGE) {
            WrapperPlayClientPluginMessage wrapper = new WrapperPlayClientPluginMessage(event);
            String channel = wrapper.getChannelName();
            byte[] data = wrapper.getData();

            User player = event.getPlayer();
            boolean bypass = false;
            if (player.getConnectionState() == ConnectionState.PLAY) {
                bypass = ((Player) event.getPlayer()).hasPermission("anticrasher.bypass");
            }
            String channelLower = channel.toLowerCase();
            if (!channel.equals(channelLower) && !bypass) {
                fail(event);
                return;
            }

            boolean isRegisterChannel = isRegisterChannel(channelLower);

            if (isRegisterChannel && data.length > 64 && !bypass) {
                fail(event);
            }
        }
    }

    private static boolean isRegisterChannel(String channelLower) {
        boolean isRegisterChannel = false;
        if (channelLower.contains(":")) {
            String[] parts = channelLower.split(":", 2);
            if (parts.length == 2 && (parts[1].equals("register") || parts[1].equals("unregister"))) {
                isRegisterChannel = true;
            }
        } else {
            if (channelLower.equals("register") || channelLower.equals("unregister")) {
                isRegisterChannel = true;
            }
        }
        return isRegisterChannel;
    }
}
