package com.github.iunius118.tolaserblade.integration.autoconfig;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = ToLaserBlade.MOD_ID)
public class ModConfig implements ConfigData {
    boolean enableLaserTrap = true;
    boolean canLaserTrapAttackPlayer = true;
    boolean canLaserTrapHeatUpFurnace = true;
    ClientConfig client = new ClientConfig();

    public boolean enableLaserTrap() {
        return enableLaserTrap;
    }

    public boolean canLaserTrapAttackPlayer() {
        return canLaserTrapAttackPlayer;
    }

    public boolean canLaserTrapHeatUpFurnace() {
        return canLaserTrapHeatUpFurnace;
    }

    public boolean canUseOriginalShaderProgram() {
        return client.useOriginalShaderProgram;
    }

    private class ClientConfig {
        boolean useOriginalShaderProgram = true;
    }
}
