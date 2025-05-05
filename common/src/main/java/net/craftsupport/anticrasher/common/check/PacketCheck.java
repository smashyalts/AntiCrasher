package net.craftsupport.anticrasher.common.check;

import com.github.retrooper.packetevents.event.ProtocolPacketEvent;
import net.craftsupport.anticrasher.api.check.Check;
import net.craftsupport.anticrasher.api.check.CheckInfo;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.common.manager.AlertManager;

public abstract class PacketCheck extends Check {

    public PacketCheck(CheckInfo checkInfo) {
        super(checkInfo);
    }

    @Override
    public void fail(ProtocolPacketEvent event, User user) {
        AlertManager.getInstance().fail(new CheckViolation(
                getCheckInfo(),
                user,
                event
        ));
    }
}