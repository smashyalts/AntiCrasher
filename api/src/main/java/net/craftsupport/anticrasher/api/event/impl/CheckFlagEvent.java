package net.craftsupport.anticrasher.api.event.impl;

import lombok.Getter;
import net.craftsupport.anticrasher.api.check.CheckInfo;
import net.craftsupport.anticrasher.api.event.Cancellable;
import net.craftsupport.anticrasher.api.user.User;
import org.jetbrains.annotations.NotNull;

/**
 * This event is called when a check is flagged.
 * This event is cancellable, meaning that you can prevent the check from being flagged.
 */
@Getter
public class CheckFlagEvent implements Cancellable {

    private boolean cancelled = false;

    @NotNull private final CheckInfo checkInfo;
    @NotNull private final User user;

    public CheckFlagEvent(@NotNull CheckInfo checkInfo, @NotNull User user) {
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
