package net.craftsupport.anticrasher.api.event.impl;

import lombok.Getter;
import net.craftsupport.anticrasher.api.check.CheckInfo;
import net.craftsupport.anticrasher.api.event.Cancellable;
import net.craftsupport.anticrasher.api.user.User;

@Getter
public class CheckFlagEvent implements Cancellable {

    private boolean cancelled = false;

    private final CheckInfo checkInfo;
    private final User user;

    public CheckFlagEvent(CheckInfo checkInfo, User user) {
        this.checkInfo = checkInfo;
        this.user = user;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
