package com.github.wintersteve25.oniutils.common.registries.worldgen;

import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.github.wintersteve25.oniutils.ONIUtils;

public class ONIBiomes {
    private static final DeferredRegister<Biome> BIOME_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BIOMES, ONIUtils.MODID);
    public static final RegistryObject<Biome> TEMPERATE_BIOME = temperate();
    public static final RegistryObject<Biome> SWAMP_BIOME = swamp();
    public static final RegistryObject<Biome> FROZEN_BIOME = frozen();
    public static final RegistryObject<Biome> OIL_BIOME = oil();
    public static final RegistryObject<Biome> VOLCANIC_BIOME = volcanic();
    public static final RegistryObject<Biome> SPACE_BIOME = space();

    private static RegistryObject<Biome> temperate() {
        var generationSettings = new BiomeGenerationSettings.Builder()
                .build();

        var mobSettings = new MobSpawnSettings.Builder()
                .build();

        return register("temperate", new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.NONE)
                .biomeCategory(Biome.BiomeCategory.NONE)
                .temperature(0.5f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .downfall(0.6f)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(3110049)
                        .foliageColorOverride(6541912)
                        .waterColor(28663)
                        .waterFogColor(28663)
                        .skyColor(28663)
                        .build())
                .generationSettings(generationSettings)
                .mobSpawnSettings(mobSettings));
    }

    private static RegistryObject<Biome> oil() {
        var generationSettings = new BiomeGenerationSettings.Builder()
                .build();

        var mobSettings = new MobSpawnSettings.Builder()
                .build();

        return register("oil", new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.NONE)
                .biomeCategory(Biome.BiomeCategory.NONE)
                .temperature(0.8f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .downfall(0f)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(3110049)
                        .foliageColorOverride(6541912)
                        .waterColor(28663)
                        .waterFogColor(28663)
                        .skyColor(28663)
                        .build())
                .generationSettings(generationSettings)
                .mobSpawnSettings(mobSettings));
    }

    private static RegistryObject<Biome> volcanic() {
        var generationSettings = new BiomeGenerationSettings.Builder()
                .build();

        var mobSettings = new MobSpawnSettings.Builder()
                .build();

        return register("volcanic", new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.NONE)
                .biomeCategory(Biome.BiomeCategory.NONE)
                .temperature(0.95f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .downfall(0f)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(3110049)
                        .foliageColorOverride(6541912)
                        .waterColor(28663)
                        .waterFogColor(28663)
                        .skyColor(28663)
                        .build())
                .generationSettings(generationSettings)
                .mobSpawnSettings(mobSettings));
    }

    private static RegistryObject<Biome> space() {
        var generationSettings = new BiomeGenerationSettings.Builder()
                .build();

        var mobSettings = new MobSpawnSettings.Builder()
                .build();

        return register("space", new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.NONE)
                .biomeCategory(Biome.BiomeCategory.NONE)
                .temperature(0.1f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .downfall(0f)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(3110049)
                        .foliageColorOverride(6541912)
                        .waterColor(28663)
                        .waterFogColor(28663)
                        .skyColor(28663)
                        .build())
                .generationSettings(generationSettings)
                .mobSpawnSettings(mobSettings));
    }

    private static RegistryObject<Biome> swamp() {
        var generationSettings = new BiomeGenerationSettings.Builder()
                .build();

        var mobSettings = new MobSpawnSettings.Builder()
                .build();

        return register("swamp", new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.NONE)
                .biomeCategory(Biome.BiomeCategory.NONE)
                .temperature(0.65f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .downfall(0f)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(3110049)
                        .foliageColorOverride(6541912)
                        .waterColor(28663)
                        .waterFogColor(28663)
                        .skyColor(28663)
                        .build())
                .generationSettings(generationSettings)
                .mobSpawnSettings(mobSettings));
    }

    private static RegistryObject<Biome> frozen() {
        var generationSettings = new BiomeGenerationSettings.Builder()
                .build();

        var mobSettings = new MobSpawnSettings.Builder()
                .build();

        return register("frozen", new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.NONE)
                .biomeCategory(Biome.BiomeCategory.NONE)
                .temperature(0.2f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .downfall(0f)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(3110049)
                        .foliageColorOverride(6541912)
                        .waterColor(28663)
                        .waterFogColor(28663)
                        .skyColor(28663)
                        .build())
                .generationSettings(generationSettings)
                .mobSpawnSettings(mobSettings));
    }
    
    private static RegistryObject<Biome> register(String id, Biome.BiomeBuilder builder) {
        return BIOME_DEFERRED_REGISTER.register(id, builder::build);
    }

    public static void register(IEventBus eventBus) {
        BIOME_DEFERRED_REGISTER.register(eventBus);
    }
}
