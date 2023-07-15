package com.github.wintersteve25.oniutils.common.registries.worldgen;

import com.github.wintersteve25.oniutils.ONIUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ONICarvers {
    private static final DeferredRegister<WorldCarver<?>> CARVER_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, ONIUtils.MODID);
    private static final DeferredRegister<ConfiguredWorldCarver<?>> CONFIGURED_WORLD_CARVER_DEFERRED_REGISTER = DeferredRegister.create(Registry.CONFIGURED_CARVER_REGISTRY, ONIUtils.MODID);
    
    public static final ResourceKey<ConfiguredWorldCarver<?>> CAVE_CARVER_TEST = ResourceKey.create(Registry.CONFIGURED_CARVER_REGISTRY, new ResourceLocation(ONIUtils.MODID, "cave"));
    
    public static void register(IEventBus bus) {
        CARVER_DEFERRED_REGISTER.register(bus);
        CONFIGURED_WORLD_CARVER_DEFERRED_REGISTER.register(bus);
    }
}
