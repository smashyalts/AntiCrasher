package net.craftsupport.anticrasher.common.check.impl.chat;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientTabComplete;
import net.craftsupport.anticrasher.api.check.CheckInfo;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.common.check.PacketCheck;

public class ChatA extends PacketCheck {
    public ChatA() {
        super(CheckInfo.builder()
                .name("Chat")
                .type("A")
                .description("Prevents tab complete crash attempts.")
                .build());
    }

    @Override
    public void handle(PacketReceiveEvent event, User user) {
        if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
            WrapperPlayClientTabComplete wrapper = new WrapperPlayClientTabComplete(event);
            String text = wrapper.getText();
            int length = text.length();

            // general length limit
            if (length > 256) {
                fail(event, user);
            }
            // paper's patch
            final int index;
            if (text.length() > 64 && ((index = text.indexOf(' ')) == -1 || index >= 64)) {
                fail(event, user);
            }
        }
    }
}
