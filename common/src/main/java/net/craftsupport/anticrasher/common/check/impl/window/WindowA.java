package net.craftsupport.anticrasher.common.check.impl.window;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import net.craftsupport.anticrasher.api.check.CheckInfo;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.common.check.PacketCheck;

public class WindowA extends PacketCheck {
    public WindowA() {
        super(CheckInfo.builder()
                .name("Window")
                .type("A")
                .description("Patch for the Negative Slot ID crash.")
                .build());
    }

    @Override
    public void handle(PacketReceiveEvent event, User user) {
        if (event.getServerVersion().isOlderThan(ServerVersion.V_1_20_5) && event.getPacketType() == PacketType.Play.Client.CLICK_WINDOW) {
            WrapperPlayClientClickWindow click = new WrapperPlayClientClickWindow(event);
            int clickType = click.getWindowClickType().ordinal();
            int button = click.getButton();
            int windowId = click.getWindowId();
            int slot = click.getSlot();

            if ((clickType == 1 || clickType == 2) && windowId >= 0 && button < 0) {
                fail(event, user);
            } else if (windowId >= 0 && clickType == 2 && slot < 0) {
                fail(event, user);
            }

            if (windowId == 0 && slot >= 36 && clickType == 2) { // Prevent creative inventory exploits
                fail(event, user);
                return;
            }
        }
    }
}
