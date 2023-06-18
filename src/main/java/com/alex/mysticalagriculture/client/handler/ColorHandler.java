package com.alex.mysticalagriculture.client.handler;

import com.alex.mysticalagriculture.api.soul.MobSoulType;
import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import com.alex.mysticalagriculture.blocks.InfusedFarmlandBlock;
import com.alex.cucumber.helper.ColorHelper;
import com.alex.cucumber.iface.Colored;
import com.alex.mysticalagriculture.init.ModItems;
import com.alex.mysticalagriculture.lib.ModCrops;
import com.alex.mysticalagriculture.registry.AugmentRegistry;
import com.alex.mysticalagriculture.registry.CropRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.RenderType;

public class ColorHandler {

    public static void onBlockColors() {
        ColorProviderRegistry.BLOCK.register(new Colored.BlockColors(), InfusedFarmlandBlock.FARMLANDS.toArray(new InfusedFarmlandBlock[0]));

        for (var crop : CropRegistry.getInstance().getCrops()) {
            if (crop.isFlowerColored() && crop.getCropBlock() != null)
                ColorProviderRegistry.BLOCK.register((state, world, pos, tint) -> crop.getFlowerColor(), crop.getCropBlock());

            BlockRenderLayerMap.INSTANCE.putBlock(crop.getCropBlock(), RenderType.cutout());
        }
    }

    public static void onItemColors() {
        ColorProviderRegistry.ITEM.register(new Colored.ItemBlockColors(), InfusedFarmlandBlock.FARMLANDS.toArray(new InfusedFarmlandBlock[0]));
        ColorProviderRegistry.ITEM.register((stack, tint) -> {
            float damage = (float) (stack.getMaxDamage() - stack.getDamageValue()) / stack.getMaxDamage();
            return ColorHelper.saturate(0x00D9D9, damage);
        }, ModItems.INFUSION_CRYSTAL);

        ColorProviderRegistry.ITEM.register((stack, tint) -> ModCrops.AIR.getEssenceColor(), ModItems.AIR_AGGLOMERATIO);
        ColorProviderRegistry.ITEM.register((stack, tint) -> ModCrops.EARTH.getEssenceColor(), ModItems.EARTH_AGGLOMERATIO);
        ColorProviderRegistry.ITEM.register((stack, tint) -> ModCrops.WATER.getEssenceColor(), ModItems.WATER_AGGLOMERATIO);
        ColorProviderRegistry.ITEM.register((stack, tint) -> ModCrops.FIRE.getEssenceColor(), ModItems.FIRE_AGGLOMERATIO);

        ColorProviderRegistry.ITEM.register((stack, tint) -> {
            MobSoulType type = MobSoulUtils.getType(stack);
            return tint == 1 && type != null ? type.getColor() : -1;
        }, ModItems.SOUL_JAR);

        for (var crop : CropRegistry.getInstance().getCrops()) {
            if (crop.isEssenceColored() && crop.getEssenceItem() != null)
                ColorProviderRegistry.ITEM.register((stack, tint) -> crop.getEssenceColor(), crop.getEssenceItem());
            if (crop.isSeedColored() && crop.getSeedsItem() != null)
                ColorProviderRegistry.ITEM.register((stack, tint) -> crop.getSeedColor(), crop.getSeedsItem());
        }

        for (var augment : AugmentRegistry.getInstance().getAugments()) {
            if (augment.getItem() != null)
                ColorProviderRegistry.ITEM.register((stack, tint) -> tint == 0 ? augment.getSecondaryColor() : tint == 1 ? augment.getPrimaryColor() : -1, augment.getItem());
        }
    }
}
