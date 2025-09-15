package com.github.iunius118.tolaserblade.network;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.config.TLBServerConfig;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.network.ConfigurationTask;

import java.util.function.Consumer;

public record SyncConfigTask(TLBServerConfig serverConfig) implements ConfigurationTask {
    public static final Type TYPE = new Type(SyncConfigS2CPayload.ID.toString());

    @Override
    public void start(Consumer<Packet<?>> consumer) {
        // Send the server-side config to the client
        var payload = new SyncConfigS2CPayload(serverConfig);
        consumer.accept(ServerConfigurationNetworking.createS2CPacket(payload));
        ToLaserBlade.LOGGER.debug("[ToLaserBlade] Send SyncConfigS2CPayload to client: {}", serverConfig);
    }

    @Override
    public Type type() {
        return TYPE;
    }
}
