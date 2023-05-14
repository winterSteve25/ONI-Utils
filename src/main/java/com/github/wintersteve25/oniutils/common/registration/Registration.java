package com.github.wintersteve25.oniutils.common.registration;

import mekanism.common.registration.impl.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import com.github.wintersteve25.oniutils.ONIUtils;
import com.github.wintersteve25.oniutils.common.registries.*;
import com.github.wintersteve25.oniutils.common.registries.worldgen.ONIBiomes;
import com.github.wintersteve25.oniutils.common.registries.worldgen.ONIFeatures;

public class Registration {
    public static final FluidDeferredRegister FLUID = new FluidDeferredRegister(ONIUtils.MODID);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ONIUtils.MODID);

    public static void init() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ONIBlocks.register(eventBus);
        ONIItems.register(eventBus);
        ONIGases.register(eventBus);
        ONISounds.register(eventBus);
        ONICapabilities.register(eventBus);
        ONIFeatures.register(eventBus);
        ONIBiomes.register(eventBus);

        EFFECTS.register(eventBus);
        FLUID.register(eventBus);

        ONITags.register();

        ONIUtils.LOGGER.info("ONIUtils Registration Completed");
    }
}