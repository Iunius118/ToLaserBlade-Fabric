package com.github.iunius118.tolaserblade.api;

import com.github.iunius118.tolaserblade.api.client.event.LaserBladeModelRegistrationCallback;

/**
 * ToLaserBlade API
 */
public class ToLaserBladeAPI {
    /**
     * Register an instance of {@link LaserBladeModelRegistrationCallback} to the Event.
     * Call on client side only.
     * The following code is an example of usage.
     *
     * <pre>{@code
     * ToLaserBladeAPI.registerModelRegistrationListener(register -> {
     *     List<LaserBladeModel> laserBladeModels = loadMyLaserBladeModels();
     *     // Register laser blade models to ToLaserBlade
     *     register.accept(laserBladeModels);
     * });
     * }</pre>
     * @param listener An instance of LaserBladeModelRegistrationCallback.
     */
    public static void registerModelRegistrationListener(LaserBladeModelRegistrationCallback listener) {
        LaserBladeModelRegistrationCallback.EVENT.register(listener);
    }
}
