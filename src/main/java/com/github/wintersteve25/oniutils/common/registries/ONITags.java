package com.github.wintersteve25.oniutils.common.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import com.github.wintersteve25.oniutils.ONIUtils;

public class ONITags {
    public static final TagKey<Block> IRREPLACEABLE_BY_POCKET = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ONIUtils.MODID, "irreplaceable_by_pocket"));

    public static void register() {
    }
}
