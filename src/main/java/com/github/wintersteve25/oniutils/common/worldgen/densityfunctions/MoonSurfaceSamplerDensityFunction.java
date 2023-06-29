package com.github.wintersteve25.oniutils.common.worldgen.densityfunctions;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.*;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public enum MoonSurfaceSamplerDensityFunction implements DensityFunction.SimpleFunction {
    INSTANCE;
    
    public static final Codec<MoonSurfaceSamplerDensityFunction> CODEC = Codec.unit(INSTANCE);
   
    private final Map<Long, Integer> heightMap;
    
    MoonSurfaceSamplerDensityFunction() {
        heightMap = new ConcurrentHashMap<>();
    }

    @Override
    public double compute(FunctionContext pContext) {
        var x = pContext.blockX();
        var y = pContext.blockY();
        var z = pContext.blockZ();
        
        var height = heightMap.computeIfAbsent(ChunkPos.asLong(x, z), pos -> {
            var rand = new Random(pos);
            return 468 + rand.nextInt(-5, 5);
        });
        
        return y > height ? 0 : 1;
    }

    @Override
    public double minValue() {
        return 0;
    }

    @Override
    public double maxValue() {
        return 1;
    }

    @Override
    public @NotNull Codec<? extends DensityFunction> codec() {
        return CODEC;
    }
}
