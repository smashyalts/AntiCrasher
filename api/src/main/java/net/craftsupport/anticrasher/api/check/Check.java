package net.craftsupport.anticrasher.api.check;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.event.ProtocolPacketEvent;
import lombok.Getter;
import net.craftsupport.anticrasher.api.user.User;

/**
 * The base class for all checks.
 * This class provides the basic structure for checks and allows for easy handling of events.
 */
@Getter
public abstract class Check {

    private final CheckInfo checkInfo;

    /**
     * Constructor for the Check class.
     *
     * @param checkInfo The information about the check, see {@link CheckInfo} for more details.
     */
    public Check(CheckInfo checkInfo) {
        this.checkInfo = checkInfo;
    }

    /**
     * Handles a S2C packet - at the moment we don't have any checks for this.
     * @param event The {@link PacketSendEvent} to handle.
     * @param user The {@link User} who the event corresponds to.
     */
    public void handle(PacketSendEvent event, User user) {}

    /**
     * Handles a C2S packet - at the moment we don't have any checks for this.
     * @param event The {@link PacketReceiveEvent} to handle.
     * @param user The {@link User} who the event corresponds to.
     */
    public void handle(PacketReceiveEvent event, User user) {}

    /**
     * Called if the checks (in {@link #handle(PacketSendEvent, User)} or {@link #handle(PacketReceiveEvent, User)} fail.
     * @param event The {@link ProtocolPacketEvent} that failed. (PacketReceiveEvent or PacketSendEvent)
     * @param user The {@link User} who the event corresponds to.
     */
    public abstract void fail(ProtocolPacketEvent event, User user);

}
