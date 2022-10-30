package com.github.iunius118.tolaserblade.common.util;

import com.mojang.authlib.GameProfile;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ServerboundClientInformationPacket;
import net.minecraft.network.protocol.game.ServerboundCommandSuggestionPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.stats.Stat;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import javax.crypto.Cipher;

public class FakePlayer extends ServerPlayer {
    public FakePlayer(ServerLevel serverLevel, GameProfile gameProfile) {
        super(serverLevel.getServer(), serverLevel, gameProfile);
        connection = new ServerGamePacketListener(this);
    }

    @Override public void displayClientMessage(Component component, boolean bl) {}
    @Override public void sendSystemMessage(Component component, boolean bl) {}
    @Override public void sendChatMessage(OutgoingChatMessage outgoingChatMessage, boolean bl, ChatType.Bound bound) {}
    @Override public void awardStat(Stat<?> stat, int i) {}
    @Override public boolean isInvulnerableTo(DamageSource damageSource) { return true; }
    @Override public boolean canHarmPlayer(Player player) { return false; }
    @Override public void die(DamageSource damageSource) {}
    @Override public void tick() {}
    @Override public void doTick() {}
    @Override public void updateOptions(ServerboundClientInformationPacket serverboundClientInformationPacket) {}

    private static class ServerGamePacketListener extends ServerGamePacketListenerImpl {

        public ServerGamePacketListener(ServerPlayer serverPlayer) {
            super(serverPlayer.server, new FakeConnection(), serverPlayer);
        }

        @Override public void disconnect(Component component) {}
        @Override public void handleMoveVehicle(ServerboundMoveVehiclePacket serverboundMoveVehiclePacket) {}
        @Override public void handleCustomCommandSuggestions(ServerboundCommandSuggestionPacket serverboundCommandSuggestionPacket) {}
        @Override public void send(Packet<?> packet, @Nullable PacketSendListener packetSendListener) {}
    }

    private static class FakeConnection extends Connection {
        public FakeConnection() {
            super(PacketFlow.SERVERBOUND);
        }

        @Override public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {}
        @Override public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) {}
        @Override protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet) {}
        @Override public void send(Packet<?> packet, @Nullable PacketSendListener packetSendListener) {}
        @Override public void tick() {}
        @Override public void disconnect(Component component) {}
        @Override public void setEncryptionKey(Cipher cipher, Cipher cipher2) {}
        @Override public void setReadOnly() {}
        @Override public void setupCompression(int i, boolean bl) {}
    }
}
