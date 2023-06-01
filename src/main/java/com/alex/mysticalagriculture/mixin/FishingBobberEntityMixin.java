package com.alex.mysticalagriculture.mixin;

import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {
    private static final TagKey<Item> FISHING_RODS = TagKey.of(Registry.ITEM_KEY, new Identifier(MOD_ID, "fishing_rods"));

    @Redirect(method = "removeIfInvalid", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    private boolean injected1(ItemStack instance, Item item) {
        return instance.isIn(FISHING_RODS) || instance.isOf(Items.FISHING_ROD);
    }

    @Redirect(method = "removeIfInvalid", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 1))
    private boolean injected2(ItemStack instance, Item item) {
        return instance.isIn(FISHING_RODS) || instance.isOf(Items.FISHING_ROD);
    }
}
