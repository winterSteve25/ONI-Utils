package wintersteve25.oniutils.common.registries.worldgen;

import mekanism.common.registration.impl.SetupFeatureDeferredRegister;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.registries.ONITags;
import wintersteve25.oniutils.common.worldgen.features.temperate.TemperatePocketFeature;
import wintersteve25.oniutils.common.worldgen.features.temperate.TemperatePocketFeatureConfiguration;

import java.util.List;

public class ONIFeatures {
    private static final DeferredRegister<Feature<?>> FEATURE_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.FEATURES, ONIUtils.MODID);
    private static final SetupFeatureDeferredRegister SETUP_FEATURE_DEFERRED_REGISTER = new SetupFeatureDeferredRegister(ONIUtils.MODID);

    public static final RegistryObject<TemperatePocketFeature> TEMPERATE_POCKET_FEATURE;
    public static final SetupFeatureDeferredRegister.MekFeature<?, ?> TEMPERATE_POCKET_FEATURE_SETUP;

    static {
        TEMPERATE_POCKET_FEATURE = FEATURE_DEFERRED_REGISTER.register("temperate_pocket", TemperatePocketFeature::new);
        TEMPERATE_POCKET_FEATURE_SETUP = SETUP_FEATURE_DEFERRED_REGISTER.register(
                "temperate_pocket",
                () -> new ConfiguredFeature<>(TEMPERATE_POCKET_FEATURE.get(), new TemperatePocketFeatureConfiguration(
                        2,
                        4,
                        4,
                        ONITags.IRREPLACEABLE_BY_POCKET
                )),
                retrogen -> List.of(
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(32), VerticalAnchor.belowTop(32)),
                        CountPlacement.of(2),
                        BiomeFilter.biome()
                )
        );


    }

    public static void register(IEventBus eventBus) {
        FEATURE_DEFERRED_REGISTER.register(eventBus);
        SETUP_FEATURE_DEFERRED_REGISTER.register(eventBus);
    }
}
