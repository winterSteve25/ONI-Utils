package com.github.wintersteve25.oniutils.common.registries.worldgen;

import com.github.wintersteve25.oniutils.ONIUtils;
import com.github.wintersteve25.oniutils.common.worldgen.carvers.PocketCarver;
import com.github.wintersteve25.oniutils.common.worldgen.carvers.PocketCarverConfig;
import net.minecraft.core.Registry;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ONICarvers {
    private static final DeferredRegister<WorldCarver<?>> CARVER_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, ONIUtils.MODID);
    private static final DeferredRegister<ConfiguredWorldCarver<?>> CONFIGURED_WORLD_CARVER_DEFERRED_REGISTER = DeferredRegister.create(Registry.CONFIGURED_CARVER_REGISTRY, ONIUtils.MODID);
    
    private static final RegistryObject<WorldCarver<PocketCarverConfig>> POCKET_CARVER = CARVER_DEFERRED_REGISTER.register("pocket_carver", PocketCarver::new);
    public static final RegistryObject<ConfiguredWorldCarver<PocketCarverConfig>> POCKET_CARVER_CONFIGURED = CONFIGURED_WORLD_CARVER_DEFERRED_REGISTER.register("pocket_carver", () -> new ConfiguredWorldCarver<>(
            POCKET_CARVER.get(),
            new PocketCarverConfig(
                    0.6f,
                    ConstantHeight.of(VerticalAnchor.absolute(256)),
                    ConstantFloat.of(1f),
                    VerticalAnchor.absolute(64),
                    CarverDebugSettings.DEFAULT
            )
    ));
    
    public static void register(IEventBus bus) {
        CARVER_DEFERRED_REGISTER.register(bus);
        CONFIGURED_WORLD_CARVER_DEFERRED_REGISTER.register(bus);
    }
}
