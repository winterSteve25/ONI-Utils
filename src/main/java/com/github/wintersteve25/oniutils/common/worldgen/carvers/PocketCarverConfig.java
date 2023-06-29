package com.github.wintersteve25.oniutils.common.worldgen.carvers;

import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class PocketCarverConfig extends CarverConfiguration {
    public PocketCarverConfig(float probability, HeightProvider y, FloatProvider yScale, VerticalAnchor lavaLevel, CarverDebugSettings debugSettings) {
        super(probability, y, yScale, lavaLevel, debugSettings);
    }
}
