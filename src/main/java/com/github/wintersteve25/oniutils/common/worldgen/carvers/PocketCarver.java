package com.github.wintersteve25.oniutils.common.worldgen.carvers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformFloat;
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
            @NotNull PocketCarverConfig config,
            @NotNull ChunkAccess chunkAccess,
            @NotNull Function<BlockPos, Holder<Biome>> biomeAccessor,
            @NotNull Random random,
            @NotNull Aquifer aquifer,
            @NotNull ChunkPos chunkPos,
            @NotNull CarvingMask carvingMask
    ) {

        double d0 = chunkPos.getBlockX(random.nextInt(16));
        double d1 = config.y.sample(random, carvingContext);
        double d2 = chunkPos.getBlockZ(random.nextInt(16));
        double d5 = UniformFloat.of(-1.0f, -0.4f).sample(random);
        
        double d6 = config.yScale.sample(random);
        float f1 = 1.0F + random.nextFloat() * 6.0F;
        
        WorldCarver.CarveSkipChecker worldcarver$carveskipchecker = (p_159202_, p_159203_, p_159204_, p_159205_, p_159206_) -> shouldSkip(p_159203_, p_159204_, p_159205_, d5);
        return this.createRoom(carvingContext, config, chunkAccess, biomeAccessor, aquifer, d0, d1, d2, f1, d6, carvingMask, worldcarver$carveskipchecker);
    }
    
    private static boolean shouldSkip(double pRelative, double pRelativeY, double pRelativeZ, double pMinrelativeY) {
        if (pRelativeY <= pMinrelativeY) {
            return true;
        } else {
            return pRelative * pRelative + pRelativeY * pRelativeY + pRelativeZ * pRelativeZ >= 1.0D;
        }
    }
    
    protected boolean createRoom(CarvingContext pContext, PocketCarverConfig pConfig, ChunkAccess pChunk, Function<BlockPos, Holder<Biome>> pBiomeAccessor, Aquifer pAquifer, double pX, double pY, double pZ, float pRadius, double pHorizontalVerticalRatio, CarvingMask pCarvingMask, WorldCarver.CarveSkipChecker pSkipChecker) {
        double d0 = 1.5D + (double)(Mth.sin(((float)Math.PI / 2F)) * pRadius);
        double d1 = d0 * pHorizontalVerticalRatio;
        return this.carveEllipsoid(pContext, pConfig, pChunk, pBiomeAccessor, pAquifer, pX + 1.0D, pY, pZ, d0, d1, pCarvingMask, pSkipChecker);
    }

    @Override
    public boolean isStartChunk(@NotNull PocketCarverConfig config, @NotNull Random random) {
        return random.nextFloat() <= config.probability;
    }
}
