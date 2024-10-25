package com.github.iunius118.tolaserblade.integration.autoconfig;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Config(name = ToLaserBlade.MOD_ID)
public class ModConfig implements ConfigData {
    boolean enableLaserTrap = true;
    boolean canLaserTrapAttackPlayer = true;
    boolean canLaserTrapHeatUpFurnace = true;
    ClientConfig client = new ClientConfig();

    public boolean isLaserTrapEnabled() {
        return enableLaserTrap;
    }

    public boolean canLaserTrapAttackPlayer() {
        return canLaserTrapAttackPlayer;
    }

    public boolean canLaserTrapHeatUpFurnace() {
        return canLaserTrapHeatUpFurnace;
    }

    public List<Item> getLaserBladeItems() {
        return getLaserBladeItemIDs().stream()
                .map(BuiltInRegistries.ITEM::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Holder.Reference::value)
                .toList();
    }

    public List<ResourceLocation> getLaserBladeItemIDs(){
        return Stream.of(client.laserBlades)
                .map(this::getResourceLocation)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(BuiltInRegistries.ITEM::containsKey)
                .toList();
    }

    private Optional<ResourceLocation> getResourceLocation(String string){
        try {
            return Optional.ofNullable(ResourceLocation.tryParse(string));
        } catch (ResourceLocationException e) {
            return Optional.empty();
        }
    }

    static class ClientConfig {
        String[] laserBlades = {"tolaserblade:laser_blade", "tolaserblade:laser_blade_fp"};
    }
}
