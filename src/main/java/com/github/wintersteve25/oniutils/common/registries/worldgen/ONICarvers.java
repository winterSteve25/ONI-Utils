package com.github.wintersteve25.oniutils.common.registries.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import com.github.wintersteve25.oniutils.ONIUtils;

public class ONICarvers {
    private static final DeferredRegister<WorldCarver<?>> CARVER_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, ONIUtils.MODID);
    
    private static <WC extends CarverConfiguration> Holder<ConfiguredWorldCarver<WC>> register(String pId, ConfiguredWorldCarver<WC> pCarver) {
//        CARVER_DEFERRED_REGISTER.register()
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_CARVER, pId, pCarver);
    }
}
