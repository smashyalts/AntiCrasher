package net.craftsupport.anticrasher.common.check.impl.item;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEditBook;
import net.craftsupport.anticrasher.api.check.CheckInfo;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.common.check.PacketCheck;

public class ItemB extends PacketCheck {
    public ItemB() {
        super(CheckInfo.builder()
                .name("Item")
                .type("B")
                .description("Prevents the book dupe exploit.")
                .build());
    }

    @Override
    public void handle(PacketReceiveEvent event, User user) {
        if (event.getPacketType() == PacketType.Play.Client.EDIT_BOOK) {
            WrapperPlayClientEditBook editBook = new WrapperPlayClientEditBook(event);
            if (editBook.getTitle() == null || editBook.getTitle().length() > 32) {
                fail(event, user);
            }
        }
    }
}
