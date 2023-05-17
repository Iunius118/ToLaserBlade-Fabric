package com.github.iunius118.tolaserblade.api.client.event;

import com.github.iunius118.tolaserblade.api.ToLaserBladeAPI;
import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import java.util.List;
import java.util.function.Consumer;

/**
 * Callback for when laser blade models should be registered.
 * Use {@link ToLaserBladeAPI#registerModelRegistrationListener} to register instances.
 */
@FunctionalInterface
public interface LaserBladeModelRegistrationCallback {
    /**
     * Use {@link ToLaserBladeAPI#registerModelRegistrationListener} to register instances.
     */
    Event<LaserBladeModelRegistrationCallback> EVENT = EventFactory.createArrayBacked(LaserBladeModelRegistrationCallback.class,
            (listeners) -> (register) -> {
                for (LaserBladeModelRegistrationCallback listener : listeners) {
                    listener.register(register);
                }
            });

    /**
     * Called to register laser blade models to ToLaserBlade.
     * @see ToLaserBladeAPI#registerModelRegistrationListener
     * @param register Laser blade model register
     */
    void register(Consumer<List<LaserBladeModel>> register);
}
