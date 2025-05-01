package net.craftsupport.anticrasher.packet.impl;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEditBook;
import net.craftsupport.anticrasher.packet.PacketCheck;

public class WindowListener extends PacketCheck {

    public WindowListener() {
        super("Slot ID / Book Dupe Crash");
    }

    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getServerVersion().isOlderThan(ServerVersion.V_1_20_5)) {
            if (event.getPacketType() == PacketType.Play.Client.CLICK_WINDOW) {
                WrapperPlayClientClickWindow click = new WrapperPlayClientClickWindow(event);
                int clickType = click.getWindowClickType().ordinal();
                int button = click.getButton();
                int windowId = click.getWindowId();
                int slot = click.getSlot();

                if ((clickType == 1 || clickType == 2) && windowId >= 0 && button < 0) {
                    fail(event);
                } else if (windowId >= 0 && clickType == 2 && slot < 0) {
                    fail(event);
                }
            }

            if (event.getPacketType() == PacketType.Play.Client.EDIT_BOOK) {
                WrapperPlayClientEditBook editBook = new WrapperPlayClientEditBook(event);
                if (editBook.getTitle() == null || editBook.getTitle().length() > 32) {
                    fail(event);
                }
            }
        }
    }
}

