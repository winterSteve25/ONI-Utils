package wintersteve25.oniutils.common.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;

public class ONIDimChunkGenerator extends ChunkGenerator {
//    private static final Codec<ONIDimChunkGenerator> CHUNK_GEN_CODEC = RecordCodecBuilder.create(instance ->
//        instance.group()
//    );

    public ONIDimChunkGenerator(BiomeProvider biomeProvider, DimensionStructuresSettings settings) {
        super(biomeProvider, settings);
    }

    @Override
    protected Codec<? extends ChunkGenerator> func_230347_a_() {
        return null;
    }

    @Override
    public ChunkGenerator func_230349_a_(long seed) {
        return null;
    }

    @Override
    public void generateSurface(WorldGenRegion region, IChunk iChunk) {

    }

    @Override
    public void func_230352_b_(IWorld world, StructureManager strMng, IChunk iChunk) {

    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmapType) {
        return 0;
    }

    @Override
    public IBlockReader func_230348_a_(int p_230348_1_, int p_230348_2_) {
        return null;
    }
}
