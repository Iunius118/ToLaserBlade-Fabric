package com.github.iunius118.tolaserblade.core.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;

public class ModParticleTypes {
    public static final SimpleParticleType LASER_TRAP_X = FabricParticleTypes.simple(true);
    public static final SimpleParticleType LASER_TRAP_Y = FabricParticleTypes.simple(true);
    public static final SimpleParticleType LASER_TRAP_Z = FabricParticleTypes.simple(true);

    public static SimpleParticleType getLaserTrapParticleType(Direction.Axis axis) {
        switch(axis) {
            case X -> {
                return LASER_TRAP_X;
            }
            case Y -> {
                return LASER_TRAP_Y;
            }
            case Z -> {
                return LASER_TRAP_Z;
            }
        }

        return LASER_TRAP_X;
    }
}
