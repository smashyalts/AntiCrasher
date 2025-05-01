package net.craftsupport.anticrasher.packet.impl;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientSelectBundleItem;
import net.craftsupport.anticrasher.packet.PacketCheck;

public class BundleListener extends PacketCheck {

    public BundleListener() {
        super("Bundle Crash");
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.SELECT_BUNDLE_ITEM) {
            WrapperPlayClientSelectBundleItem packet = new WrapperPlayClientSelectBundleItem(event);

            if (packet.getSelectedItemIndex() < 0 && packet.getSelectedItemIndex() != -1) {
                fail(event);
            }
        }
    }
}
