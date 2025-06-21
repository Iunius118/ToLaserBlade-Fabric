package com.github.iunius118.tolaserblade.mixin.client;

import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(targets = "net.minecraft.client.data.models.ModelProvider$ItemInfoCollector")
public interface ItemInfoCollectorInvoker {

    @Invoker
    void invokeRegister(Item item, ClientItem clientItem);
}
