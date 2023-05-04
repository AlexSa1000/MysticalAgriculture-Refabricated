package com.alex.mysticalagriculture.client.handler;

import com.alex.mysticalagriculture.api.soul.MobSoulType;
import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import com.alex.mysticalagriculture.blocks.InfusedFarmlandBlock;
import com.alex.mysticalagriculture.init.Items;
import com.alex.mysticalagriculture.lib.ModCrops;
import com.alex.mysticalagriculture.registry.AugmentRegistry;
import com.alex.mysticalagriculture.registry.CropRegistry;
import com.alex.mysticalagriculture.zucchini.helper.ColorHelper;
import com.alex.mysticalagriculture.zucchini.iface.Colored;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;

public class ColorHandler {

    public static void onBlockColors() {
        ColorProviderRegistry.BLOCK.register(new Colored.BlockColors(), InfusedFarmlandBlock.FARMLANDS.toArray(new InfusedFarmlandBlock[0]));

        for (var crop : CropRegistry.getInstance().getCrops()) {
            if (crop.isFlowerColored() && crop.getCropBlock() != null)
                ColorProviderRegistry.BLOCK.register((state, world, pos, tint) -> crop.getFlowerColor(), crop.getCropBlock());

            BlockRenderLayerMap.INSTANCE.putBlock(crop.getCropBlock(), RenderLayer.getCutout());
        }
    }

    public static void onItemColors() {
        ColorProviderRegistry.ITEM.register(new Colored.ItemBlockColors(), InfusedFarmlandBlock.FARMLANDS.toArray(new InfusedFarmlandBlock[0]));
        ColorProviderRegistry.ITEM.register((stack, tint) -> {
            float damage = (float) (stack.getMaxDamage() - stack.getDamage()) / stack.getMaxDamage();
            return ColorHelper.saturate(0x00D9D9, damage);
        }, Items.INFUSION_CRYSTAL);

        ColorProviderRegistry.ITEM.register((stack, tint) -> ModCrops.AIR.getEssenceColor(), Items.AIR_AGGLOMERATIO);
        ColorProviderRegistry.ITEM.register((stack, tint) -> ModCrops.EARTH.getEssenceColor(), Items.EARTH_AGGLOMERATIO);
        ColorProviderRegistry.ITEM.register((stack, tint) -> ModCrops.WATER.getEssenceColor(), Items.WATER_AGGLOMERATIO);
        ColorProviderRegistry.ITEM.register((stack, tint) -> ModCrops.FIRE.getEssenceColor(), Items.FIRE_AGGLOMERATIO);

        ColorProviderRegistry.ITEM.register((stack, tint) -> {
            MobSoulType type = MobSoulUtils.getType(stack);
            return tint == 1 && type != null ? type.getColor() : -1;
        }, Items.SOUL_JAR);

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
