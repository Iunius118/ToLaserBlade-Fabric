package com.github.iunius118.tolaserblade.api;

import com.github.iunius118.tolaserblade.api.client.event.LaserBladeModelRegistrationCallback;
import com.github.iunius118.tolaserblade.api.core.laserblade.LaserBladeState;
import net.minecraft.world.item.ItemStack;

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
        com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager.MODEL_REGISTRATION_EVENT.register(listener);
    }

    /**
     * Get the state of a laser blade.
     * @param itemStack Item stack of a laser blade.
     * @return State of the laser blade.
     */
    public static LaserBladeState getLaserBladeState(ItemStack itemStack) {
        return com.github.iunius118.tolaserblade.core.laserblade.LaserBlade.of(itemStack).getState();
    }
}
