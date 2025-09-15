package com.github.iunius118.tolaserblade.network;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.config.TLBServerConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SyncConfigS2CPayload(TLBServerConfig serverConfig) implements CustomPacketPayload {
    public static final ResourceLocation ID = ToLaserBlade.makeId("sync_config");
    public static final CustomPacketPayload.Type<SyncConfigS2CPayload> TYPE = new CustomPacketPayload.Type<>(ID);
    public static final StreamCodec<FriendlyByteBuf, SyncConfigS2CPayload> CODEC = StreamCodec.of(
            SyncConfigS2CPayload::toNetwork, SyncConfigS2CPayload::fromNetwork);

    private static SyncConfigS2CPayload fromNetwork(FriendlyByteBuf buffer) {
        float laserBladeDamageModifier = ByteBufCodecs.FLOAT.decode(buffer);
        float laserBladeSpeedModifier = ByteBufCodecs.FLOAT.decode(buffer);
        int maxAttackDamageUpgradeCount = ByteBufCodecs.INT.decode(buffer);
        int attackDamageUpgradeMultiplier = ByteBufCodecs.INT.decode(buffer);
        boolean isLaserTrapEnabled = ByteBufCodecs.BOOL.decode(buffer);
        boolean canLaserTrapAttackPlayer = ByteBufCodecs.BOOL.decode(buffer);
        boolean canLaserTrapHeatUpFurnace = ByteBufCodecs.BOOL.decode(buffer);

        return new SyncConfigS2CPayload(
                new TLBServerConfig(
                        laserBladeDamageModifier,
                        laserBladeSpeedModifier,
                        maxAttackDamageUpgradeCount,
                        attackDamageUpgradeMultiplier,
                        isLaserTrapEnabled,
                        canLaserTrapAttackPlayer,
                        canLaserTrapHeatUpFurnace
                )
        );
    }

    private static void toNetwork(FriendlyByteBuf buffer, SyncConfigS2CPayload payload) {
        ByteBufCodecs.FLOAT.encode(buffer, payload.serverConfig.laserBladeDamageModifier());
        ByteBufCodecs.FLOAT.encode(buffer, payload.serverConfig.laserBladeSpeedModifier());
        ByteBufCodecs.INT.encode(buffer, payload.serverConfig.maxAttackDamageUpgradeCount());
        ByteBufCodecs.INT.encode(buffer, payload.serverConfig.attackDamageUpgradeMultiplier());
        ByteBufCodecs.BOOL.encode(buffer, payload.serverConfig.isLaserTrapEnabled());
        ByteBufCodecs.BOOL.encode(buffer, payload.serverConfig.canLaserTrapAttackPlayer());
        ByteBufCodecs.BOOL.encode(buffer, payload.serverConfig.canLaserTrapHeatUpFurnace());
    }

    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
