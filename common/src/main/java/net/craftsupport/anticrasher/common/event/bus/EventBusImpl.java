package net.craftsupport.anticrasher.common.event.bus;

import net.craftsupport.anticrasher.api.event.Event;
import net.craftsupport.anticrasher.api.event.EventPriority;
import net.craftsupport.anticrasher.api.event.Listener;
import net.craftsupport.anticrasher.api.event.bus.EventBus;
import net.craftsupport.anticrasher.api.event.bus.Subscribe;
import net.craftsupport.anticrasher.common.util.ACLogger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBusImpl implements EventBus {

    private final List<Listener> listeners = new CopyOnWriteArrayList<>();

    @Override
    public void subscribe(Listener listener) {
        int subscribeAnnotations = 0;

        for (Method method : listener.getClass().getMethods()) {
            if (method.isAnnotationPresent(Subscribe.class)) subscribeAnnotations++;
        }

        if (subscribeAnnotations > 0) {
            throw new IllegalArgumentException("A plugin attempted to register %s as a listener, but no public methods were found with the @Subscribe annotation.".formatted(listener.getClass().getSimpleName()));
        }

        ACLogger.info(String.format("Registered listener [%s] with [%s] methods.", listener.getClass().getSimpleName(), subscribeAnnotations));
        listeners.add(listener);
    }

    @Override
    public void unsubscribe(Listener listener) {
        ACLogger.warn("Unregistered listener [%s]", listener.getClass().getSimpleName());
        listeners.remove(listener);
    }

    @Override
    public void emit(Event event) {
        for (EventPriority priority : EventPriority.values()) {
            for (Listener listener : listeners) {
                for (Method method : listener.getClass().getMethods()) {
                    if (method.getParameterCount() != 1 ||
                            !method.getParameters()[0].getType().isAssignableFrom(event.getClass())) {
                        continue;
                    }

                    Subscribe annotation = method.getAnnotation(Subscribe.class);
                    if (annotation == null || annotation.priority() != priority) {
                        continue;
                    }

                    invoke(method, listener, event);
                }
            }
        }
    }

    private void invoke(Method method, Listener listener, Object... args) {
        method.setAccessible(true);

        try {
            method.invoke(listener, args);
        } catch (InvocationTargetException e) {
            ACLogger.fatal(
                    String.format("Failed to access method %s on listener %s", method.getName(), listener.getClass().getSimpleName()),
                    e
            );
        } catch (IllegalAccessException e) {
            ACLogger.fatal(
                    String.format("Failed to invoke method %s on listener %s", method.getName(), listener.getClass().getSimpleName()),
                    e
            );
        }
    }
}
