package com.github.wintersteve25.oniutils.common.registries.worldgen;

import com.github.wintersteve25.oniutils.ONIUtils;
import com.github.wintersteve25.oniutils.common.worldgen.densityfunctions.MoonSurfaceSamplerDensityFunction;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class ONIDensityFunction {
    private static final DeferredRegister<Codec<? extends DensityFunction>> DENSITY_FUNCTION_DEFERRED_REGISTER = DeferredRegister.create(Registry.DENSITY_FUNCTION_TYPE_REGISTRY, ONIUtils.MODID);
    
    public static void register(IEventBus eventBus) {
        DENSITY_FUNCTION_DEFERRED_REGISTER.register("moon_surface_sampler", () -> MoonSurfaceSamplerDensityFunction.CODEC);
        DENSITY_FUNCTION_DEFERRED_REGISTER.register(eventBus);
    }
}
