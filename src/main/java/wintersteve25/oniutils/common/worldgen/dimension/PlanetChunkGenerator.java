package wintersteve25.oniutils.common.worldgen.dimension;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.jetbrains.annotations.NotNull;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.utils.ONIConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class PlanetChunkGenerator extends ChunkGenerator {

    private static final MultiNoiseBiomeSource.Preset PLANET = new MultiNoiseBiomeSource.Preset(
            new ResourceLocation(ONIUtils.MODID, "planet_chunkgen"),
            biomeRegistry -> {
                ImmutableList.Builder<Pair<Climate.ParameterPoint, Holder<Biome>>> builder = ImmutableList.builder();
                return new Climate.ParameterList<>(builder.build());
            }
    );

    private static final Codec<PlanetChunkGenerator> CODEC = RecordCodecBuilder.create(instance -> {
        return instance
                .group(
                        RegistryOps.retrieveRegistry(Registry.STRUCTURE_SET_REGISTRY).forGetter(PlanetChunkGenerator::getStructureSet),
                        RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY).forGetter(PlanetChunkGenerator::getBiomeRegistry),
                        Codec.LONG.fieldOf("seed").stable().forGetter(PlanetChunkGenerator::getSeed)
                )
                .apply(instance, PlanetChunkGenerator::new);
    });

    private static Optional<HolderSet<StructureSet>> getOverridingStructures(Registry<StructureSet> structureSets) {
        var set = structureSets.getOrCreateTag(TagKey.create(Registry.STRUCTURE_SET_REGISTRY, ONIConstants.Tags.PLANET_SPAWNABLE_STRUCTURE));
        return Optional.of(set);
    }


    private final Registry<Biome> biomeRegistry;

    public PlanetChunkGenerator(Registry<StructureSet> pStructureSets, Registry<Biome> biomeRegistry, long seed) {
        this(pStructureSets, PLANET.biomeSource(biomeRegistry), biomeRegistry, seed);
    }

    public PlanetChunkGenerator(Registry<StructureSet> pStructureSets, BiomeSource source, Registry<Biome> biomeRegistry, long seed) {
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
        return new PlanetChunkGenerator(getStructureSet(), getBiomeRegistry(), pSeed);
    }

    @Override
    public Climate.Sampler climateSampler() {
        return new Climate.Sampler(
                DensityFunctions.constant(0),
                DensityFunctions.constant(0),
                DensityFunctions.constant(0),
                DensityFunctions.constant(0),
                DensityFunctions.constant(0),
                DensityFunctions.constant(0),
                new ArrayList<>()
        );
    }

    @Override
    public void applyCarvers(WorldGenRegion pLevel, long pSeed, BiomeManager pBiomeManager, StructureFeatureManager pStructureFeatureManager, ChunkAccess pChunk, GenerationStep.Carving pStep) {

    }

    @Override
    public void buildSurface(WorldGenRegion pLevel, StructureFeatureManager pStructureFeatureManager, ChunkAccess pChunk) {
        if (!SharedConstants.debugVoidTerrain(pChunk.getPos())) {
            BlockState bedrock = Blocks.BEDROCK.defaultBlockState();
            BlockState stone = Blocks.STONE.defaultBlockState();

            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

            int x;
            int z;

            for (x = 0; x < 16; x++) {
                for (z = 0; z < 16; z++) {
                    pChunk.setBlockState(pos.set(x, -64, z), bedrock, false);
                }
            }

            for (x = 0; x < 16; x++) {
                for (z = 0; z < 16; z++) {
                    for (int y = 1 ; y < getGenDepth() ; y++) {
                        pChunk.setBlockState(pos.set(x, y, z), stone, false);
                    }
                }
            }
        }
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion pLevel) {

    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor p_187748_, Blender p_187749_, StructureFeatureManager p_187750_, ChunkAccess p_187751_) {
        return null;
    }

    @Override
    public int getBaseHeight(int pX, int pZ, @NotNull Heightmap.Types pType, @NotNull LevelHeightAccessor pLevel) {
        return 0;
    }

    @Override
    public NoiseColumn getBaseColumn(int pX, int pZ, LevelHeightAccessor pLevel) {
        return null;
    }

    @Override
    public void addDebugScreenInfo(List<String> p_208054_, BlockPos p_208055_) {

    }

    @Override
    public int getSeaLevel() {
        return -64;
    }

    @Override
    public int getGenDepth() {
        return 319;
    }

    @Override
    public int getMinY() {
        return -64;
    }
}
