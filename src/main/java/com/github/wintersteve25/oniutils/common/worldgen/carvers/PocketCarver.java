package com.github.wintersteve25.oniutils.common.worldgen.carvers;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

import java.util.Random;
import java.util.function.Function;

public class PocketCarver extends WorldCarver<PocketCarverConfig> {
    public PocketCarver(Codec<PocketCarverConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean carve(CarvingContext carvingContext, PocketCarverConfig pocketCarverConfig, ChunkAccess chunkAccess, Function<BlockPos, Holder<Biome>> function, Random random, Aquifer aquifer, ChunkPos chunkPos, CarvingMask carvingMask) {
        return false;
    }

    @Override
    public boolean isStartChunk(PocketCarverConfig pocketCarverConfig, Random random) {
        return false;
    }
}
