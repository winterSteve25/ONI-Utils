package wintersteve25.oniutils.common.world.dimension;

import com.mojang.serialization.Codec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

import java.util.List;

public class ONIDimBiomeProvider extends BiomeProvider {
    protected ONIDimBiomeProvider(List<Biome> biomes) {
        super(biomes);
    }

    @Override
    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return null;
    }

    @Override
    public BiomeProvider getBiomeProvider(long seed) {
        return null;
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        return null;
    }
}
