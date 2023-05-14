package com.github.wintersteve25.oniutils.common.registries.worldgen;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import com.github.wintersteve25.oniutils.ONIUtils;
import com.github.wintersteve25.oniutils.common.worldgen.AsteroidChunkGenerator;

public class ONIDimensions {
    public static final ResourceKey<Level> ASTEROID = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(ONIUtils.MODID, "asteroid"));

    public static void register() {
        Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(ONIUtils.MODID, "asteroid_chunkgen"), AsteroidChunkGenerator.CODEC);
    }
}
