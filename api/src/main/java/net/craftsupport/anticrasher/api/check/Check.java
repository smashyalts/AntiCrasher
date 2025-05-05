package net.craftsupport.anticrasher.api.check;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.event.ProtocolPacketEvent;
import lombok.Getter;
import net.craftsupport.anticrasher.api.user.User;

@Getter
public abstract class Check {

    private final CheckInfo checkInfo;

    public Check(CheckInfo checkInfo) {
        this.checkInfo = checkInfo;
    }

    public void handle(PacketSendEvent event, User user) {}

    public void handle(PacketReceiveEvent event, User user) {}

    public abstract void fail(ProtocolPacketEvent event, User user);

}
