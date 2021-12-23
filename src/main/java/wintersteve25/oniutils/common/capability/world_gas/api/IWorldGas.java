package wintersteve25.oniutils.common.capability.world_gas.api;

import mekanism.api.chemical.gas.Gas;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import wintersteve25.oniutils.common.capability.world_gas.api.chemistry.GasStackWrapper;

import java.util.Map;

public interface IWorldGas {
    void update();

    Map<GasStackWrapper, BlockPos> getGasMap();

    void setGasMap(Map<GasStackWrapper, BlockPos> map);

    Map<GasStackWrapper, Integer> getGasAtChunk(BlockPos pos);

    Map<GasStackWrapper, Integer> getGasAtChunk(ChunkPos pos);

    int getYFromGas(Gas gas, ChunkPos pos);

    boolean addGasToChunk(GasStackWrapper gas, BlockPos pos);

    boolean addGasToChunk(GasStackWrapper gas, ChunkPos pos);
}
