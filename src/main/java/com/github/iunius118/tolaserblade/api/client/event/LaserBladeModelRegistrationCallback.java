package com.github.iunius118.tolaserblade.api.client.event;

import com.github.iunius118.tolaserblade.api.ToLaserBladeAPI;
import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;

import java.util.List;
import java.util.function.Consumer;

/**
 * Callback for when laser blade models should be registered.
 * Use {@link ToLaserBladeAPI#registerModelRegistrationListener} to register instances.
 */
@FunctionalInterface
public interface LaserBladeModelRegistrationCallback {
    /**
     * Called to register laser blade models to ToLaserBlade.
     * @see ToLaserBladeAPI#registerModelRegistrationListener
     * @param register Laser blade model register
     */
    void register(Consumer<List<LaserBladeModel>> register);
}
