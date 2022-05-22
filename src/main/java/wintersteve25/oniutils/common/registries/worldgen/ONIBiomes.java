package wintersteve25.oniutils.common.registries.worldgen;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import wintersteve25.oniutils.ONIUtils;

public class ONIBiomes {
    private static final DeferredRegister<Biome> BIOME_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BIOMES, ONIUtils.MODID);

    public static final RegistryObject<Biome> TEMPERATE_BIOME;

    static {
        var temperateGenerationSettings = new BiomeGenerationSettings.Builder()
                .addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, ONIFeatures.TEMPERATE_POCKET_FEATURE_SETUP.placedFeature())
                .build();
        var temperateMobSpawningSettings = new MobSpawnSettings.Builder()
                .build();

        TEMPERATE_BIOME = register("temperate", new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.NONE)
                .biomeCategory(Biome.BiomeCategory.NONE)
                .temperature(0.5f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .downfall(0.6f)
                .specialEffects(
                        new BiomeSpecialEffects.Builder()
                                .fogColor(3110049)
                                .foliageColorOverride(6541912)
                                .waterColor(28663)
                                .waterFogColor(28663)
                                .skyColor(28663)
                                .build()
                )
                .generationSettings(temperateGenerationSettings)
                .mobSpawnSettings(temperateMobSpawningSettings));
    }

    private static RegistryObject<Biome> register(String id, Biome.BiomeBuilder builder) {
        return BIOME_DEFERRED_REGISTER.register(id, builder::build);
    }

    public static void register(IEventBus eventBus) {
        BIOME_DEFERRED_REGISTER.register(eventBus);
    }
}
