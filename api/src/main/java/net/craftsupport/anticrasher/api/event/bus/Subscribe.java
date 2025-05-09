package net.craftsupport.anticrasher.api.event.bus;

import net.craftsupport.anticrasher.api.event.EventPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used in [Listener] classes to mark methods that should be called when an event is fired.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {

    /**
     * Whether the event should be ignored if it is cancelled.
     * If this is set to true, the listener method will not be called.
     */
    boolean ignoreCancelled() default false;

    /**
     * The priority of the event, determined by {@link EventPriority}
     * This determines the order that event listeners receive the event.
     * The MONITOR priority should be used for monitoring data in events, and not for modifying them.
     */
    EventPriority priority() default EventPriority.NORMAL;
}
