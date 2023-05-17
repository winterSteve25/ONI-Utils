package com.github.wintersteve25.oniutils.common.registries.worldgen;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import com.github.wintersteve25.oniutils.ONIUtils;

public class ONIDimensions {
    public static final ResourceKey<Level> ASTEROID = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(ONIUtils.MODID, "asteroid"));

    public static void register() {
    }
}
