package net.craftsupport.anticrasher.api.event;

/**
 * The superclass of all events, implemented only by {@link Cancellable} (for now).
 * New events will either implement this class or a subclass (e.g. {@link Cancellable})
 */
public interface Event {}