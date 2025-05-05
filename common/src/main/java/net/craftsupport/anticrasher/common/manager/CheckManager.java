package net.craftsupport.anticrasher.common.manager;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import info.preva1l.trashcan.flavor.annotations.Configure;
import info.preva1l.trashcan.flavor.annotations.Service;
import lombok.Getter;
import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.check.Check;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.common.check.impl.channel.ChannelA;
import net.craftsupport.anticrasher.common.check.impl.chat.ChatA;
import net.craftsupport.anticrasher.common.check.impl.item.ItemA;
import net.craftsupport.anticrasher.common.check.impl.item.ItemB;
import net.craftsupport.anticrasher.common.check.impl.window.WindowA;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckManager implements PacketListener {
    @Getter public static final CheckManager instance = new CheckManager();

    public List<Check> checks = new ArrayList<>();

    @Configure
    public void initialise() {
        checks = initializeChecks();
        PacketEvents.getAPI().getEventManager().registerListener(this, PacketListenerPriority.LOWEST);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        User user = AntiCrasherAPI.getInstance().getUserManager().get(event.getUser().getUUID());
        if (user != null && user.hasPermission("anticrasher.bypass")) return;

        for (Check check : checks) {
            check.handle(event, user);
        }
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        User user = AntiCrasherAPI.getInstance().getUserManager().get(event.getUser().getUUID());
        if (user != null && user.hasPermission("anticrasher.bypass")) return;

        for (Check check : checks) {
            check.handle(event, user);
        }
    }

    private List<Check> initializeChecks() {
        return List.of(
                new ItemA(),
                new ItemB(),
                new ChatA(),
                new WindowA(),
                new ChannelA()
        );
    }
}
