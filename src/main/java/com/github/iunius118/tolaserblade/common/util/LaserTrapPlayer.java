package com.github.iunius118.tolaserblade.common.util;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.particle.ModParticleTypes;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.UUID;

public class LaserTrapPlayer extends FakePlayer {
    private static final GameProfile PROFILE = new GameProfile(UUID.fromString("2BDD19A3-9616-417A-8797-EE805F5FF9E3"), "[LaserBlade]");

    private LaserTrapPlayer(ServerLevel serverLevel) {
        super(serverLevel, PROFILE);
    }

    public void initInventory(ItemStack currentStack) {
        final var inventory = this.getInventory();
        inventory.clearContent();

        // Set given item stack in main hand
        inventory.selected = 0;
        inventory.setItem(0, currentStack);

        // Apply attack damage from main hand item
        getAttributes().addTransientAttributeModifiers(currentStack.getAttributeModifiers(EquipmentSlot.MAINHAND));
    }

    public static LaserTrapPlayer get(ServerLevel serverLevel, BlockPos trapPos, ItemStack itemStackHeld) {
        final var laserTrapPlayer = new LaserTrapPlayer(serverLevel);
        laserTrapPlayer.setPos(trapPos.getX() + 0.5D, trapPos.getY(), trapPos.getZ() + 0.5D);
        laserTrapPlayer.initInventory(itemStackHeld.copy());
        return laserTrapPlayer;
    }

    public void attackEntities(Direction dir) {
        BlockPos trapPos = blockPosition();
        BlockPos targetPos = trapPos.relative(dir);
        AABB aabb = new AABB(targetPos).inflate(0.5D);
        List<Entity> targetEntities = level.getEntities((Entity) null, aabb, this::canHitEntity);

        float attackDamage = (float) getAttribute(Attributes.ATTACK_DAMAGE).getValue();
        int fireLevel = EnchantmentHelper.getFireAspect(this);
        ItemStack itemStack = getMainHandItem();

        for (var targetEntity : targetEntities) {
            float totalDamage = attackDamage + getDamageBonus(itemStack, targetEntity);
            if (canBurn(targetEntity, fireLevel)) targetEntity.setSecondsOnFire(Math.min(fireLevel, 1));
            targetEntity.hurt(DamageSource.playerAttack(this), totalDamage);
            EnchantmentHelper.doPostDamageEffects(this, targetEntity);
        }

        spawnParticle(dir, targetPos, itemStack);
    }

    private boolean canHitEntity(Entity entity) {
        if (!entity.isSpectator() && entity.isAlive() && entity.isPickable()) {
            MinecraftServer server = level.getServer();
            // Check if the trap can attack players
            boolean isPvpAllowed = (server != null && server.isPvpAllowed());
            return isPvpAllowed || !(entity instanceof Player);
        } else {
            return false;
        }
    }

    private float getDamageBonus(ItemStack itemStack, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            return EnchantmentHelper.getDamageBonus(itemStack, livingEntity.getMobType());
        } else {
            return 0;
        }
    }

    private boolean canBurn(Entity entity, int fireAspectLevel) {
        return fireAspectLevel > 0 && (entity instanceof Mob || entity instanceof Player);
    }

    private void spawnParticle(Direction dir, BlockPos effectPos, ItemStack itemStack) {
        if (!(level instanceof ServerLevel serverLevel)) return;

        var laserTrapParticleType = ModParticleTypes.getLaserTrapParticleType(dir.getAxis());
        var vecPos = new Vec3(effectPos.getX(), effectPos.getY(), effectPos.getZ()).add(0.5, 0.5, 0.5);
        var laserBladeVisual = LaserBlade.visualOf(itemStack);
        var outerColor = laserBladeVisual.getOuterColor();
        var color4F = Color4F.of((outerColor.isSubtractColor ? ~outerColor.color : outerColor.color) | 0xFF000000);
        serverLevel.sendParticles(laserTrapParticleType, vecPos.x, vecPos.y, vecPos.z, 0, color4F.r(), color4F.g(), color4F.b(), 1);
    }
}
