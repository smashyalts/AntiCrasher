package net.craftsupport.anticrasher.api.event;

/**
 * This would be implemented alongside {@link Event} to allow for the event to be cancelled.
 */
public interface Cancellable extends Event {

    /**
     * @return true if the event is cancelled, false otherwise.
     */
    boolean isCancelled();

    /**
     * Modify the cancellation state of this event.
     * @param cancelled The new cancelled state of the event.
     */
    void setCancelled(boolean cancelled);
}
