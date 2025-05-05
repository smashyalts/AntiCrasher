package net.craftsupport.anticrasher.common.check.impl.channel;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;
import net.craftsupport.anticrasher.api.check.CheckInfo;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.common.check.PacketCheck;

public class ChannelA extends PacketCheck {
    public ChannelA() {
        super(CheckInfo.builder()
                .name("Channel")
                .type("A")
                .description("Prevents malicious plugin messages.")
                .build());
    }

    @Override
    public void handle(PacketReceiveEvent event, User user) {
        if (event.getPacketType() != PacketType.Play.Client.PLUGIN_MESSAGE) {
            return;
        }

        WrapperPlayClientPluginMessage wrapper = new WrapperPlayClientPluginMessage(event);
        String channel = wrapper.getChannelName();
        byte[] data = wrapper.getData();

        if (!channel.equals(channel.toLowerCase())) {
            fail(event, user);
            return;
        }

        if (isRegisterChannel(channel) && data.length > 64) {
            fail(event, user);
        }
    }

    private boolean isRegisterChannel(String channel) {
        if (channel.contains(":")) {
            String[] parts = channel.split(":", 2);
            return parts.length == 2 && (parts[1].equals("register") || parts[1].equals("unregister"));
        }
        return channel.equals("register") || channel.equals("unregister");
    }
}