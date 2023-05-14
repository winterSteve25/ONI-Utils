package com.github.wintersteve25.oniutils.common.registries.worldgen;

import mekanism.common.registration.impl.SetupFeatureDeferredRegister;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import com.github.wintersteve25.oniutils.ONIUtils;

public class ONIFeatures {
    private static final DeferredRegister<Feature<?>> FEATURE_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.FEATURES, ONIUtils.MODID);
    private static final SetupFeatureDeferredRegister SETUP_FEATURE_DEFERRED_REGISTER = new SetupFeatureDeferredRegister(ONIUtils.MODID);

    public static void register(IEventBus eventBus) {
        FEATURE_DEFERRED_REGISTER.register(eventBus);
        SETUP_FEATURE_DEFERRED_REGISTER.register(eventBus);
    }
}
