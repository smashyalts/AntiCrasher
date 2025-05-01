package net.craftsupport.anticrasher.packet.impl;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientTabComplete;
import net.craftsupport.anticrasher.packet.PacketCheck;
import org.bukkit.entity.Player;

public class TabCompleteListener extends PacketCheck {

    public TabCompleteListener() {
        super("(Possible) Tab Complete Crash");
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
            WrapperPlayClientTabComplete wrapper = new WrapperPlayClientTabComplete(event);
            String text = wrapper.getText();
            final int length = text.length();
            Player player = event.getPlayer();

            // general length limit
            if (length > 256 && !player.hasPermission("anticrasher.bypass")) {
                fail(event);
            }
            // paper's patch
            final int index;
            if (text.length() > 64 && ((index = text.indexOf(' ')) == -1 && !player.hasPermission("anticrasher.bypass") || index >= 64 && !player.hasPermission("anticrasher.bypass"))) {
                fail(event);
            }
        }
    }
}
