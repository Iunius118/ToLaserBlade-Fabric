package com.github.iunius118.tolaserblade.api.client.event;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import java.util.List;
import java.util.function.Consumer;

public class ToLaserBladeClientEvent {
    public static final Event<ToLaserBladeClientEvent.RegisterModelCallback> REGISTER_MODEL =
            EventFactory.createArrayBacked(ToLaserBladeClientEvent.RegisterModelCallback.class,
                    (listeners) -> (register) -> {
                        for (ToLaserBladeClientEvent.RegisterModelCallback listener : listeners) {
                            listener.registerModels(register);
                        }
                    });

    @FunctionalInterface
    public interface RegisterModelCallback {
        void registerModels(Consumer<List<LaserBladeModel>> register);
    }
}
