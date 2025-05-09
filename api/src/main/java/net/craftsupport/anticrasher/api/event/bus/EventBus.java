package net.craftsupport.anticrasher.api.event.bus;

import net.craftsupport.anticrasher.api.event.Event;
import net.craftsupport.anticrasher.api.event.Listener;

/**
 * The event bus for AntiCrasher.
 * This will handle all {@link Event}s, and will be where you register your listeners.
 */
public interface EventBus {

    /**
     * Registers a listener to the event bus.
     * @param listener The listener to register.
     */
    void subscribe(Listener listener);

    /**
     * Unregisters a listener from the event bus.
     * @param listener The listener to unregister.
     */
    void unsubscribe(Listener listener);

    /**
     * Emit an {@link Event} - registered listeners will subsequently be called.
     * @param event The event to emit.
     */
    void emit(Event event);
}
