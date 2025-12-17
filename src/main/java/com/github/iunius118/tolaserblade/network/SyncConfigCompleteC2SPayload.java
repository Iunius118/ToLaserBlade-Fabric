package com.github.iunius118.tolaserblade.network;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public class SyncConfigCompleteC2SPayload implements CustomPacketPayload {
    public static final SyncConfigCompleteC2SPayload INSTANCE = new SyncConfigCompleteC2SPayload();
    public static final Identifier ID = ToLaserBlade.makeId("sync_config_complete");
    public static final CustomPacketPayload.Type<SyncConfigCompleteC2SPayload> TYPE = new CustomPacketPayload.Type<>(ID);
    public static final StreamCodec<FriendlyByteBuf, SyncConfigCompleteC2SPayload> CODEC = StreamCodec.unit(INSTANCE);

    private SyncConfigCompleteC2SPayload() { }

    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
