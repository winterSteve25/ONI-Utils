package com.github.wintersteve25.oniutils.common.worldgen.carvers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.function.Function;

public class PocketCarver extends WorldCarver<PocketCarverConfig> {
    public PocketCarver() {
        super(PocketCarverConfig.CODEC);
    }

    @Override
    public boolean carve(
            @NotNull CarvingContext carvingContext,
            @NotNull PocketCarverConfig pocketCarverConfig,
            @NotNull ChunkAccess chunkAccess,
            @NotNull Function<BlockPos, Holder<Biome>> function,
            @NotNull Random random,
            @NotNull Aquifer aquifer,
            @NotNull ChunkPos chunkPos,
            @NotNull CarvingMask carvingMask
    ) {
        
        var carveSkipChecker = new CarveSkipChecker() {
            @Override
            public boolean shouldSkip(CarvingContext pContext, double pRelativeX, double pRelativeY, double pRelativeZ, int pY) {
                return false;
            }
        };
        
        return carveEllipsoid(
                carvingContext,
                pocketCarverConfig,
                chunkAccess,
                function,
                aquifer,
                chunkPos.getMiddleBlockX(),
                256,
                chunkPos.getMiddleBlockZ(),
                8,
                4,
                carvingMask,
                carveSkipChecker
        );
    }

    @Override
    public boolean isStartChunk(@NotNull PocketCarverConfig config, @NotNull Random random) {
        return true;
//        return random.nextFloat() <= config.probability;
    }
}
