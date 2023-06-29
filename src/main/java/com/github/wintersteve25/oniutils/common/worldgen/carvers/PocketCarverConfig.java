package com.github.wintersteve25.oniutils.common.worldgen.carvers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class PocketCarverConfig extends CarverConfiguration {

    public static final Codec<PocketCarverConfig> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    CarverConfiguration.CODEC.forGetter(config -> config)
            ).apply(instance, PocketCarverConfig::new));

    private PocketCarverConfig(CarverConfiguration configuration) {
        this(configuration.probability, configuration.y, configuration.yScale, configuration.lavaLevel, configuration.debugSettings);
    }

    public PocketCarverConfig(float probability, HeightProvider y, FloatProvider yScale, VerticalAnchor lavaLevel, CarverDebugSettings debugSettings) {
        super(probability, y, yScale, lavaLevel, debugSettings);
    }
}
