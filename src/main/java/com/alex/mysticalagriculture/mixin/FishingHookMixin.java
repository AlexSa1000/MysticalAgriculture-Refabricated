package com.alex.mysticalagriculture.mixin;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

@Mixin(FishingHook.class)
public class FishingHookMixin {
    private static final TagKey<Item> FISHING_RODS = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "fishing_rods"));

    @Redirect(method = "shouldStopFishing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 0))
    private boolean injected1(ItemStack instance, Item item) {
        return instance.is(FISHING_RODS) || instance.is(Items.FISHING_ROD);
    }

    @Redirect(method = "shouldStopFishing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 1))
    private boolean injected2(ItemStack instance, Item item) {
        return instance.is(FISHING_RODS) || instance.is(Items.FISHING_ROD);
    }
}
