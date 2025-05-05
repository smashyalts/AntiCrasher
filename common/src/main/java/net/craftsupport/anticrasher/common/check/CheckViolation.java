package net.craftsupport.anticrasher.common.check;

import com.github.retrooper.packetevents.event.ProtocolPacketEvent;
import net.craftsupport.anticrasher.api.check.CheckInfo;
import net.craftsupport.anticrasher.api.user.User;

public record CheckViolation(
        CheckInfo check,
        User user,
        ProtocolPacketEvent event
) {}
