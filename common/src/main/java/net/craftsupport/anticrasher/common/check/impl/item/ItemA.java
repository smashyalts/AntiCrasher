package net.craftsupport.anticrasher.common.check.impl.item;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientSelectBundleItem;
import net.craftsupport.anticrasher.api.check.CheckInfo;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.common.check.PacketCheck;

public class ItemA extends PacketCheck {
    public ItemA() {
        super(CheckInfo.builder()
                .name("Item")
                .type("A")
                .description("Prevents the bundle crash exploit.")
                .build());
    }

    @Override
    public void handle(PacketReceiveEvent event, User user) {
        if (event.getPacketType() == PacketType.Play.Client.SELECT_BUNDLE_ITEM) {
            WrapperPlayClientSelectBundleItem packet = new WrapperPlayClientSelectBundleItem(event);

            if (packet.getSelectedItemIndex() < 0 && packet.getSelectedItemIndex() != -1) {
                fail(event, user);
            }
        }
    }
}
