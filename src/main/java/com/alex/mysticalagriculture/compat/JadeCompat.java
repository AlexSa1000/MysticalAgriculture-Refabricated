package com.alex.mysticalagriculture.compat;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.api.crop.CropProvider;
import com.alex.mysticalagriculture.api.farmland.EssenceFarmLand;
import com.alex.mysticalagriculture.blocks.InferiumCropBlock;
import com.alex.mysticalagriculture.blocks.InfusedFarmlandBlock;
import com.alex.mysticalagriculture.blocks.MysticalCropBlock;
import com.alex.mysticalagriculture.lib.ModTooltips;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

@WailaPlugin
public class JadeCompat implements IWailaPlugin {
    private static final Identifier CROP_PROVIDER = new Identifier(MysticalAgriculture.MOD_ID, "crop");
    private static final Identifier INFERIUM_CROP_PROVIDER = new Identifier(MysticalAgriculture.MOD_ID, "inferium_crop");
    private static final Identifier INFUSED_FARMLAND_PROVIDER = new Identifier(MysticalAgriculture.MOD_ID, "infused_farmland");

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(new IBlockComponentProvider() {
            @Override
            public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                var block = accessor.getBlock();
                var crop = ((CropProvider) block).getCrop();

                tooltip.add(ModTooltips.TIER.args(crop.getTier().getDisplayName()).build());

                var pos = accessor.getPosition();
                var downPos = pos.down();
                var level = accessor.getLevel();
                var belowBlock = level.getBlockState(downPos).getBlock();

                var secondaryChance = crop.getSecondaryChance(belowBlock);
                if (secondaryChance > 0) {
                    var chanceText = Text.literal(String.valueOf((int) (secondaryChance * 100)))
                            .append("%")
                            .formatted(crop.getTier().getTextColor());

                    tooltip.add(ModTooltips.SECONDARY_CHANCE.args(chanceText).build());
                }

                var crux = crop.getCruxBlock();
                if (crux != null) {
                    var stack = new ItemStack(crux);
                    tooltip.add(ModTooltips.REQUIRES_CRUX.args(stack.getName()).build());
                }

                /*var biomes = crop.getRequiredBiomes();
                if (!biomes.isEmpty()) {
                    var biome = level.getBiome(pos);
                    var id = Registry.BIOME_SOURCE.getId(biome.value());
                    if (!biomes.contains(id)) {
                        tooltip.add(ModTooltips.INVALID_BIOME.color(Formatting.RED).build());
                    }
                }*/
            }

            @Override
            public Identifier getUid() {
                return CROP_PROVIDER;
            }
        }, MysticalCropBlock.class);

        registration.registerBlockComponent(new IBlockComponentProvider() {
            @Override
            public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                var block = accessor.getBlock();
                var crop = ((CropProvider) block).getCrop();
                var downPos = accessor.getPosition().down();
                var belowBlock = accessor.getLevel().getBlockState(downPos).getBlock();

                int output = 100;
                if (belowBlock instanceof EssenceFarmLand farmland) {
                    int tier = farmland.getTier().getValue();
                    output = (tier * 50) + 50;
                }

                var inferiumOutputText = Text.literal(String.valueOf(output)).append("%").formatted(crop.getTier().getTextColor());

                tooltip.add(ModTooltips.INFERIUM_OUTPUT.args(inferiumOutputText).build());
            }

            @Override
            public Identifier getUid() {
                return INFERIUM_CROP_PROVIDER;
            }
        }, InferiumCropBlock.class);

        registration.registerBlockComponent(new IBlockComponentProvider() {
            @Override
            public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                var block = accessor.getBlock();
                var farmland = (EssenceFarmLand) block;

                tooltip.add(ModTooltips.TIER.args(farmland.getTier().getDisplayName()).build());
            }

            @Override
            public Identifier getUid() {
                return INFUSED_FARMLAND_PROVIDER;
            }
        }, InfusedFarmlandBlock.class);
    }
}
