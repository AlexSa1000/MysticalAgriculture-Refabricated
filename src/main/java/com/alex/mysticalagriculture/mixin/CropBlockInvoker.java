package com.alex.mysticalagriculture.mixin;

import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CropBlock.class)
public interface CropBlockInvoker {
    @Invoker("getSeedsItem")
    ItemConvertible invokerGetSeedsItem();
}
