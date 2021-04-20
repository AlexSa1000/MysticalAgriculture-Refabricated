package com.alex.mysticalagriculture.util.blockentity;


import com.alex.mysticalagriculture.util.block.BaseBlock;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.Tag;

public class BaseBlockEntity extends BaseBlock {

    public BaseBlockEntity(Material material, BlockSoundGroup sound, float hardness, float resistance, Tag<Item> tool) {
        super(material, sound, hardness, resistance, tool);
    }
}
