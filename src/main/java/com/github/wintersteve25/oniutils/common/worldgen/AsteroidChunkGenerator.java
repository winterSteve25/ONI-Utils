package com.github.wintersteve25.oniutils.common.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.jetbrains.annotations.NotNull;
import com.github.wintersteve25.oniutils.common.registries.worldgen.ONIBiomes;
import com.github.wintersteve25.oniutils.common.registries.ONIBlocks;
import com.github.wintersteve25.oniutils.common.utils.ONIConstants;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class AsteroidChunkGenerator extends ChunkGenerator {

    public static final Codec<AsteroidChunkGenerator> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    RegistryOps.retrieveRegistry(Registry.STRUCTURE_SET_REGISTRY).forGetter(AsteroidChunkGenerator::getStructureSet),
                    RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY).forGetter(AsteroidChunkGenerator::getBiomeRegistry),
                    Codec.LONG.fieldOf("seed").stable().forGetter(AsteroidChunkGenerator::getSeed)
            ).apply(instance, AsteroidChunkGenerator::new));

    private static Optional<HolderSet<StructureSet>> getOverridingStructures(Registry<StructureSet> structureSets) {
        var set = structureSets.getOrCreateTag(TagKey.create(Registry.STRUCTURE_SET_REGISTRY, ONIConstants.Tags.PLANET_SPAWNABLE_STRUCTURE));
        return Optional.of(set);
    }

    private final Registry<Biome> biomeRegistry;

    public AsteroidChunkGenerator(Registry<StructureSet> pStructureSets, Registry<Biome> biomeRegistry, long seed) {
        this(pStructureSets, new FixedBiomeSource(biomeRegistry.getHolderOrThrow(ONIBiomes.TEMPERATE_BIOME.getKey())), biomeRegistry, seed);
    }

    public AsteroidChunkGenerator(Registry<StructureSet> pStructureSets, BiomeSource source, Registry<Biome> biomeRegistry, long seed) {
        super(pStructureSets, getOverridingStructures(pStructureSets), source, source, seed);
        this.biomeRegistry = biomeRegistry;
    }

    private Registry<StructureSet> getStructureSet() {
        return structureSets;
    }

    private Registry<Biome> getBiomeRegistry() {
        return biomeRegistry;
    }

    private long getSeed() {
        return ringPlacementSeed;
    }

    @Override
    protected @NotNull Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public @NotNull ChunkGenerator withSeed(long pSeed) {
        return new AsteroidChunkGenerator(getStructureSet(), getBiomeRegistry(), pSeed);
    }

    @Override
    public Climate.Sampler climateSampler() {
        return Climate.empty();
    }

    @Override
    public void applyCarvers(WorldGenRegion pLevel, long pSeed, BiomeManager pBiomeManager, StructureFeatureManager pStructureFeatureManager, ChunkAccess pChunk, GenerationStep.Carving pStep) {
    }

    @Override
    public void buildSurface(WorldGenRegion pLevel, StructureFeatureManager pStructureFeatureManager, ChunkAccess pChunk) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        Heightmap heightmap = pChunk.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
        Heightmap heightmap1 = pChunk.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);

        var height = pChunk.getHeight();

        for(int i = 0; i < height; ++i) {
            BlockState blockstate = i == 0 || i == height - 1 ? Blocks.BEDROCK.defaultBlockState() : ONIBlocks.NonFunctionals.ABYSSALITE.getBlock().defaultBlockState();

            int j = pChunk.getMinBuildHeight() + i;

            for(int k = 0; k < 16; ++k) {
                for(int l = 0; l < 16; ++l) {
                    pChunk.setBlockState(mutablePos.set(k, j, l), blockstate, false);
                    heightmap.update(k, j, l, blockstate);
                    heightmap1.update(k, j, l, blockstate);
                }
            }
        }
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, StructureFeatureManager structureFeatureManager, ChunkAccess chunkAccess) {
        return CompletableFuture.completedFuture(chunkAccess);
    }

    @Override
    public NoiseColumn getBaseColumn(int pX, int pZ, LevelHeightAccessor pLevel) {
        int y = getBaseHeight(pX, pZ, Heightmap.Types.WORLD_SURFACE_WG, pLevel);
        BlockState stone = ONIBlocks.NonFunctionals.ABYSSALITE.getBlock().defaultBlockState();
        BlockState[] states = new BlockState[y];

        states[0] = Blocks.BEDROCK.defaultBlockState();

        for (int i = 1 ; i < y ; i++) {
            states[i] = stone;
        }

        states[states.length - 1] = Blocks.BEDROCK.defaultBlockState();

        return new NoiseColumn(pLevel.getMinBuildHeight(), states);
    }

    @Override
    public int getBaseHeight(int pX, int pZ, Heightmap.Types pType, LevelHeightAccessor pLevel) {
        return pLevel.getMaxBuildHeight();
    }

    @Override
    public void addDebugScreenInfo(List<String> p_208054_, BlockPos p_208055_) {
    }

    public void spawnOriginalMobs(WorldGenRegion pLevel) {
    }

    public int getMinY() {
        return 0;
    }

    public int getGenDepth() {
        return 384;
    }

    public int getSeaLevel() {
        return -63;
    }
}
